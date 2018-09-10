package com.example.bonvoath.tms.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bonvoath.tms.R;

import org.json.JSONException;
import org.json.JSONObject;

public class OrderMapInfoDetail extends DialogFragment {
    JSONObject mOrder;
    String TAG = "OrderMapInfoDetail";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.dialog_map_info_detail, container, false);
        txtName = mView.findViewById(R.id.txtName);
        txtNum = mView.findViewById(R.id.txtNum);
        txtPrice = mView.findViewById(R.id.txtPrice);
        txtRemark = mView.findViewById(R.id.txtRemark);
        btnClose = mView.findViewById(R.id.btnClose);
        if(mOrder != null){
            try{
                txtName.setText(mOrder.getString("Name"));
                txtNum.setText(mOrder.getString("OrderNum"));
                txtPrice.setText(mOrder.getString("Price"));
                if(!mOrder.isNull("Remark"))
                    txtRemark.setText(mOrder.getString("Remark"));
            }catch (JSONException e){
                Log.e(TAG, e.getMessage());
            }
        }
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getDialog() != null)
            if(getDialog().getWindow() != null)
                getDialog().getWindow().setWindowAnimations(R.style.DialogMapInfo);
    }

    public void setData(JSONObject order){
        mOrder = order;
    }

    TextView txtName;
    TextView txtNum;
    TextView txtPrice;
    TextView txtRemark;
    ImageButton btnClose;
}
