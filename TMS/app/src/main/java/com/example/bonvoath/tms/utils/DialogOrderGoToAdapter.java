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

import java.util.List;

public class DialogOrderGoToAdapter extends RecyclerView.Adapter<DialogOrderGoToAdapter.ViewHolder> {
    private Context mContext;
    private List<OrderMaster> data;

    public DialogOrderGoToAdapter(Context context){
        mContext = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_assign_list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderMaster order = data.get(position);
        holder.txt_order_num.setText(order.getOrderNumber());
        holder.txt_order_date.setText(order.getOrderDate());
        holder.txt_address.setText(order.getAddress());
        holder.txt_remark.setText(order.getRemark());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void  setData(List<OrderMaster> orderMasters){
        data = orderMasters;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_order_num;
        TextView txt_order_date;
        TextView txt_remark;
        TextView txt_address;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_order_num = itemView.findViewById(R.id.txt_order_num);
            txt_order_date = itemView.findViewById(R.id.txt_order_date);
            txt_address = itemView.findViewById(R.id.txt_address);
            txt_remark = itemView.findViewById(R.id.txt_remark);
        }
    }
}