package com.example.bonvoath.tms.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bonvoath.tms.Entities.OrderMaster;
import com.example.bonvoath.tms.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogOrderGoToMap extends DialogFragment implements View.OnClickListener {
    private List<OrderMaster> data;
    private String truckNumber;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.dialog_order_list_item, container, false);
        uRecyclerView = mView.findViewById(R.id.list_order_item);
        DialogOrderGoToAdapter adapter = new DialogOrderGoToAdapter(getActivity());
        adapter.setData(data);
        uRecyclerView.setAdapter(adapter);
        uRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        btnClose = mView.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(this);

        btnAssign = mView.findViewById(R.id.btn_assign);
        btnAssign.setOnClickListener(this);

        return mView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_assign:
                assignOrder();
                getDialog().dismiss();
                break;
            case R.id.btnClose:
                getDialog().dismiss();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getDialog() != null)
            if(getDialog().getWindow() != null)
                getDialog().getWindow().setWindowAnimations(R.style.DialogMapInfo);
    }

    public void  setData(Context context, List<OrderMaster> orderMasters, String truckNumber){
        data = orderMasters;
        this.truckNumber = truckNumber;
        this.mContext = context;
    }

    private void assignOrder(){
        String url = TMSLib.getUrl(mContext, R.string.assign_order_truck);
        List<String> orderNums = new ArrayList<>();
        for (OrderMaster order: data) {
            orderNums.add(order.getOrderNumber());
        }
        Map<String, Object> params = new HashMap<>();
        params.put("TruckNum", truckNumber);
        params.put("OrderNums", orderNums);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    boolean isError = response.getBoolean("IsError");
                    if(!isError){
                        Toast toast = Toast.makeText(mContext, "Orders are assign success.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }catch (JSONException e){
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(mContext).add(request);
    }

    RecyclerView uRecyclerView;
    ImageButton btnClose;
    Button btnAssign;
}
