package com.example.bonvoath.tms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bonvoath.tms.utils.LoginHelper;
import com.example.bonvoath.tms.utils.TMSLib;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderPayActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;

    // Variable
    List<String> order_numbers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(R.string.pay);
        }

        initializeComponent();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(getWindow() != null) {
            //getWindow().setWindowAnimations(R.style.DialogMapInfo);
            this.overridePendingTransition(R.anim.slide_in_left_to_right,
                    R.anim.slide_out_left_to_right);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_confirm_pickup:
                String truck = LoginHelper.getTruckNumber(getApplicationContext());
                Intent intent = getIntent();
                String order_num = intent.getStringExtra("OrderNum");
                order_numbers.add(order_num);
                //Toast.makeText(getApplicationContext(), "Order number: " + order_num, Toast.LENGTH_LONG).show();
                confirm_pickup(truck, order_numbers);
                break;
        }
    }

    private void initializeComponent(){
        Button btnConfirm = findViewById(R.id.btn_confirm_pickup);
        btnConfirm.setOnClickListener(this);
    }

    private void confirm_pickup(String truck_number, List<String> orderNums){
        String url = TMSLib.getUrl(this, R.string.assign_order_truck);
        Map<String, Object> params = new HashMap<>();
        params.put("TruckNum", truck_number);
        params.put("OrderNums", orderNums);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,
                new JSONObject(params), confirmPickupCallback, confirmPickupErrorCallback);
        Volley.newRequestQueue(this).add(request);
    }

    private Response.Listener<JSONObject> confirmPickupCallback = new Response.Listener<JSONObject>(){
        @Override
        public void onResponse(JSONObject response) {
            try {
                boolean is_error = response.getBoolean("IsError");
                if(!is_error){
                    finish();
                }
            } catch (JSONException e) {
            }
        }
    };

    private Response.ErrorListener confirmPickupErrorCallback = new Response.ErrorListener(){
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    };
}
