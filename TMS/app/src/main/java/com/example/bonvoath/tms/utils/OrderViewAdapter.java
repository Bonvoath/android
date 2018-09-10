package com.example.bonvoath.tms.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bonvoath.tms.Entities.OrderMaster;
import com.example.bonvoath.tms.R;

import java.util.ArrayList;

public class OrderViewAdapter extends RecyclerView.Adapter<OrderViewAdapter.ViewHolder> {

    private static final String TAG = "OrderViewAdapter";
    private ArrayList<OrderMaster> mOrderMasters;
    private Context mContext;

    public OrderViewAdapter(Context context, ArrayList<OrderMaster> orderMasters){
        mOrderMasters = orderMasters;
        mContext = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_order_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderMaster order = mOrderMasters.get(position);
        holder.txt_order_num.setText(order.getOrderNumber());
        holder.txt_order_date.setText(order.getOrderDate());
        holder.txt_order_address.setText(order.getAddress());
    }

    @Override
    public int getItemCount() {
        return mOrderMasters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_order_num;
        TextView txt_order_date;
        TextView txt_order_address;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_order_num = itemView.findViewById(R.id.txt_order_num);
            txt_order_date = itemView.findViewById(R.id.txt_order_date);
            txt_order_address= itemView.findViewById(R.id.txt_order_address);
        }
    }
}