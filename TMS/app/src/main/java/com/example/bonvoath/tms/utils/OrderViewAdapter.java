package com.example.bonvoath.tms.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bonvoath.tms.Entities.DataSet;
import com.example.bonvoath.tms.Entities.OrderMaster;
import com.example.bonvoath.tms.R;

public class OrderViewAdapter extends RecyclerView.Adapter<OrderViewAdapter.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_order_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderMaster order = DataSet.OrderMasters.get(position);
        holder.txt_order_num.setText(order.getOrderNumber());
        holder.txt_order_date.setText(order.getRemark());
    }

    @Override
    public int getItemCount() {
        return DataSet.OrderMasters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_order_num;
        TextView txt_order_date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_order_num = itemView.findViewById(R.id.txtTag);
            txt_order_date = itemView.findViewById(R.id.txtRemark);
        }
    }
}
