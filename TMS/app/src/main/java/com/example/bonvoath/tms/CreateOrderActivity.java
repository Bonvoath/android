package com.example.bonvoath.tms;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bonvoath.tms.Entities.DataSet;
import com.example.bonvoath.tms.Entities.GoogleAddressResult;
import com.example.bonvoath.tms.utils.LoginHelper;
import com.example.bonvoath.tms.utils.TMSLib;
import com.example.bonvoath.tms.utils.swipes.DatePickerDialogFragment;
import com.example.bonvoath.tms.utils.swipes.GoogleSearchAddressDialogFragment;
import com.example.bonvoath.tms.utils.swipes.TimePickerDialogFragment;
import com.volokh.danylo.hashtaghelper.HashTagHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateOrderActivity extends AppCompatActivity implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener, View.OnFocusChangeListener,
        GoogleSearchAddressDialogFragment.OnGoogleSearchDialogFragmentListener {
    SharedPreferences mSharedPreferences;
    Toolbar toolbar;
    EditText txtDeliveryDate, txtDeliveryTime, txtOrderNo, txtPrice, txtCustomerName, txtDeliveryAddress,
            txtArea1, txtArea2, txtLocality, txtSubLocality,
            txtLat, txtLong, txtCountry, txtRemark;
    DatePickerDialogFragment date;
    TimePickerDialogFragment time;
    Calendar calendar;

    HashTagHelper hashTagHelper;

    GoogleSearchAddressDialogFragment google;
    ProgressDialog progressDialog;
    ImageView btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        initializeComponent();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btnsave){
            saveOrder();
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();
        if(!hasFocus && id == R.id.txt_delivery_address){
            String address = txtDeliveryAddress.getText().toString();
            if(!address.equals("")) {
                searchGoogleAddress(address);
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        txtDeliveryDate.setText(getDeliveryDate());
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        txtDeliveryTime.setText(getTime());
    }

    @Override
    public void onSelectGoogleResult(View v, GoogleAddressResult sender) {
        String area1 = sender.getArea1().equals("null") ?"":sender.getArea1();
        String area2 = sender.getArea2().equals("null") ?"":sender.getArea2();
        String locality = sender.getLocality().equals("null")?"":sender.getLocality();
        String sub = sender.getSubLocality().equals("null")?"":sender.getSubLocality();
        txtArea1.setText(area1);
        txtArea2.setText(area2);
        txtLocality.setText(locality);
        txtSubLocality.setText(sub);
        txtLat.setText(sender.getLat());
        txtLong.setText(sender.getLng());
        txtCountry.setText(sender.getCountry());
    }

    private void initializeComponent(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(R.string.create_new_order);
        }

        calendar = Calendar.getInstance();
        txtDeliveryDate = findViewById(R.id.txt_delivery_date);
        txtDeliveryDate.setText(getDeliveryDate());
        txtDeliveryTime = findViewById(R.id.txt_delivery_time);
        txtDeliveryTime.setText(getTime());
        time = new TimePickerDialogFragment();
        date = new DatePickerDialogFragment();


        ImageButton btnDate = findViewById(R.id.btnDate);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date.setMonth(calendar.get(Calendar.MONTH));
                date.setDay(calendar.get(Calendar.DAY_OF_MONTH));
                date.setYear(calendar.get(Calendar.YEAR));
                date.show(getFragmentManager(), getClass().getName());
            }
        });

        ImageButton btnTime = findViewById(R.id.btnTime);
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time.setHour(calendar.get(Calendar.HOUR_OF_DAY));
                time.setMinute(calendar.get(Calendar.MINUTE));
                time.show(getFragmentManager(), getClass().getName());
            }
        });

        progressDialog = new ProgressDialog(CreateOrderActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        btnSave = findViewById(R.id.btnsave);
        btnSave.setOnClickListener(this);

        txtOrderNo = findViewById(R.id.txt_order_no);
        txtCustomerName = findViewById(R.id.txt_customer_name);
        txtCustomerName.requestFocus();
        txtPrice = findViewById(R.id.txt_price);

        txtDeliveryAddress = findViewById(R.id.txt_delivery_address);
        txtDeliveryAddress.setOnFocusChangeListener(this);

        txtArea1 = findViewById(R.id.txt_area1);
        txtArea2 = findViewById(R.id.txt_area2);
        txtLocality = findViewById(R.id.txt_locality);
        txtSubLocality = findViewById(R.id.txt_sub_locality);
        txtLat = findViewById(R.id.txt_lat);
        txtLong = findViewById(R.id.txt_long);
        txtCountry = findViewById(R.id.txt_country);
        txtRemark = findViewById(R.id.txt_remark);
        hashTagHelper = HashTagHelper.Creator.create(getResources().getColor(R.color.colorPrimary), null);
        hashTagHelper.handle(txtRemark);
        loadingOrderNumber();
    }

    private String getDeliveryDate(){
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        return  month + "/" + day + "/" + year;
    }

    private String getTime(){
        String hour = new DecimalFormat("00").format(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = new DecimalFormat("00").format(calendar.get(Calendar.MINUTE));

        return  hour + ":" + minute;
    }

    private void loadingOrderNumber()
    {
        progressDialog.show();
        DataSet.OrderMasters = new ArrayList<>();
        String url = TMSLib.getUrl(this, R.string.get_order_number);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    boolean isError = response.getBoolean("IsError");
                    if(!isError){
                        JSONObject data = response.getJSONObject("Data");
                        if(data != null) {
                            String value = data.getString("Value");
                            txtOrderNo.setText(value);
                        }
                    }
                    progressDialog.dismiss();
                }catch (JSONException e){
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });

        Volley.newRequestQueue(this).add(request);
    }

    private void searchGoogleAddress(String address){
        progressDialog.show();
        google = GoogleSearchAddressDialogFragment.instance("Google Search Address");
        String url = TMSLib.getUrl(this, R.string.get_google_search_address);
        Map<String, Object> params = new HashMap<>();
        params.put("address", address);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    boolean isError = response.getBoolean("IsError");
                    if(!isError){
                        JSONArray data = response.getJSONArray("Data");
                        if(data != null) {
                            for (int i = 0; i < data.length(); i++){
                                JSONObject jsonObject = (JSONObject)data.get(i);
                                GoogleAddressResult obj = new GoogleAddressResult();
                                obj.setLat(jsonObject.getString("Lat"));
                                obj.setLng(jsonObject.getString("Long"));
                                obj.setArea1(jsonObject.getString("Area1"));
                                obj.setArea2(jsonObject.getString("Area2"));
                                obj.setLocality(jsonObject.getString("Locality"));
                                obj.setSubLocality(jsonObject.getString("SubLocality"));
                                obj.setCountry(jsonObject.getString("Country"));
                                google.addItem(obj);
                            }
                            progressDialog.dismiss();
                            google.setOnGoogleSearchDialogFragmentListener(CreateOrderActivity.this);
                            google.show(getFragmentManager(), getClass().getName());
                        }
                    }
                }catch (JSONException e){
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });

        Volley.newRequestQueue(this).add(request);
    }

    private void saveOrder()
    {
        progressDialog.show();
        String num = txtOrderNo.getText().toString();
        String name = txtCustomerName.getText().toString();
        String ddate = txtDeliveryDate.getText().toString().replace("/", "-");
        String dtime = txtDeliveryTime.getText().toString();
        String price = txtPrice.getText().toString();
        String remark = txtRemark.getText().toString();
        String shipAddress = txtDeliveryAddress.getText().toString();
        String area1 = txtArea1.getText().toString();
        String area2 = txtArea2.getText().toString();
        String locality = txtLocality.getText().toString();
        String subLocality = txtSubLocality.getText().toString();
        String country = txtCountry.getText().toString();
        String lat = txtLat.getText().toString();
        String lng = txtLong.getText().toString();

        Map<String, String> order = new HashMap<>();
        order.put("OrderNum", num);
        order.put("CustomerNum", name);
        order.put("DeliveryTime", dtime);
        order.put("ExtraCharge", price);
        order.put("ShipDate", ddate);
        order.put("Remark", remark);
        order.put("ShipAddress", shipAddress);
        order.put("AdministrativeArea1", area1);
        order.put("AdministrativeArea2", area2);
        order.put("Locality", locality);
        order.put("SubLocality", subLocality);
        order.put("Country", country);
        order.put("Lat", lat);
        order.put("Long", lng);
        order.put("CreatedBy", LoginHelper.getUserId(getApplicationContext()));

        List<Object> imageList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("order", order);
        params.put("images", imageList);
        String url = TMSLib.getUrl(this, R.string.save_order);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,
                new JSONObject(params), responseCallback, errorCallback);
        Volley.newRequestQueue(this).add(request);
    }

    Response.Listener<JSONObject> responseCallback = new Response.Listener<JSONObject>(){
        @Override
        public void onResponse(JSONObject response) {
            try
            {
                boolean isError = response.getBoolean("IsError");
                if(!isError){
                    finish();
                }
                progressDialog.dismiss();
            }catch (JSONException e){
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Response: " + e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    };

    Response.ErrorListener errorCallback = new Response.ErrorListener(){
        @Override
        public void onErrorResponse(VolleyError error) {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
}
