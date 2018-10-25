package com.example.bonvoath.tms.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.bonvoath.tms.Entities.OrderMaster;
import com.example.bonvoath.tms.OrderCommentActivity;
import com.example.bonvoath.tms.OrderPayActivity;
import com.example.bonvoath.tms.R;
import com.example.bonvoath.tms.utils.swipes.SwipeAwayDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MapInfoDialogFragment extends SwipeAwayDialogFragment implements View.OnClickListener {
    public interface OnMapInfoDialogFragmentListener{
        void onClick(View sender);
    }
    OnMapInfoDialogFragmentListener mOnMapInfoDialogFragmentListener;
    OrderMaster mOrder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.dialog_map_info_detail, container, false);
        txtName = mView.findViewById(R.id.txtName);
        txtNum = mView.findViewById(R.id.txtNum);
        txtPrice = mView.findViewById(R.id.txtPrice);
        txtRemark = mView.findViewById(R.id.txtRemark);
        btnClose = mView.findViewById(R.id.btnClose);
        mDemoSlider = mView.findViewById(R.id.slider);
        txtName.setText(mOrder.getName());
        txtNum.setText(mOrder.getOrderNumber());
        txtPrice.setText(mOrder.getPrice());
        txtRemark.setText(mOrder.getRemark());
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        getOrderImage();

        initializeComponent(mView);

        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getDialog() != null)
            if(getDialog().getWindow() != null)
                getDialog().getWindow().setWindowAnimations(R.style.DialogMapInfo);
    }

    @Override
    public void onClick(View v) {
        mOnMapInfoDialogFragmentListener.onClick(v);
    }

    @Override
    public boolean onSwipedAway(boolean toRight) {
        if(!toRight)
        {
            startActivity(new Intent(getActivity(), OrderCommentActivity.class));
        }else {
            startActivity(new Intent(getActivity(), OrderPayActivity.class));
        }

        return false;
    }

    public static MapInfoDialogFragment instance(String title) {
        MapInfoDialogFragment f = new MapInfoDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("type", title);
        f.setArguments(args);

        return f;
    }

    public void setOnMapInfoDialogFragmentListener(OnMapInfoDialogFragmentListener onMapInfoDialogFragmentListener){
        mOnMapInfoDialogFragmentListener = onMapInfoDialogFragmentListener;
    }
    public void setData(OrderMaster order){
        mOrder = order;
    }

    private void getOrderImage()
    {
        if(getActivity() == null || mOrder == null)
            return;
        String orderNum = mOrder.getOrderNumber();
        String url = TMSLib.getUrl(getActivity(), R.string.get_order_image_by_order_number);
        Map<String, Object> params = new HashMap<>();
        params.put("OrderNum", orderNum);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    boolean isError = response.getBoolean("IsError");
                    if(!isError){
                        JSONArray jsonArray = response.getJSONArray("Data");
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject obj = (JSONObject)jsonArray.get(i);
                            String img_url = getResources().getString(R.string.server_address) + "/image/size250/" + obj.getString("Id");
                            TextSliderView textSliderView = new TextSliderView(getActivity());
                            textSliderView
                                    .image(img_url)
                                    .setScaleType(BaseSliderView.ScaleType.Fit);
                            textSliderView.bundle(new Bundle());

                            mDemoSlider.addSlider(textSliderView);
                        }
                    }
                }catch (JSONException e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        if(getActivity() != null)
            Volley.newRequestQueue(getActivity()).add(request);
    }

    private void initializeComponent(View parent){
        Button btnMessage = parent.findViewById(R.id.btn_message);
        btnMessage.setOnClickListener(this);

        Button btnPay = parent.findViewById(R.id.btn_pay);
        btnPay.setOnClickListener(this);
    }

    TextView txtName;
    TextView txtNum;
    TextView txtPrice;
    TextView txtRemark;
    ImageButton btnClose;
    SliderLayout mDemoSlider;
}
