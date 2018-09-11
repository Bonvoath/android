package com.example.bonvoath.tms;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bonvoath.tms.Entities.DataSet;
import com.example.bonvoath.tms.Entities.OrderMaster;
import com.example.bonvoath.tms.utils.OrderViewAdapter;
import com.example.bonvoath.tms.utils.TMSLib;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {
    private SharedPreferences mSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        mSharedPreferences = getApplicationContext().getSharedPreferences("Auth", MODE_PRIVATE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Orders");
        }
        orderData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void orderData()
    {
        DataSet.OrderMasters = new ArrayList<>();
        String url = TMSLib.getUrl(this, R.string.order_get_by_truck);
        String key = mSharedPreferences.getString("TruckNumber", "");
        Map<String, Object> params = new HashMap<>();
        params.put("TruckNum", key);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    boolean isError = response.getBoolean("IsError");
                    if(!isError){
                        JSONArray data = response.getJSONArray("Data");
                        for(int i=0;i<data.length(); i++){
                            JSONObject obj = (JSONObject) data.get(i);
                            String num = obj.getString("OrderNum");
                            String date = obj.getString("OrderDate");
                            String address = obj.getString("Address");
                            DataSet.OrderMasters.add(new OrderMaster(num, date, address));
                        }
                        bindingOrderToRecycleView();
                    }
                }catch (JSONException e){
                    Toast.makeText(OrderActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrderActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(this).add(request);
    }

    private void bindingOrderToRecycleView(){
        RecyclerView recyclerView = findViewById(R.id.view_orders);
        OrderViewAdapter adapter = new OrderViewAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
