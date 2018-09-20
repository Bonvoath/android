package com.example.bonvoath.tms.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bonvoath.tms.Entities.DataSet;
import com.example.bonvoath.tms.Entities.OrderMaster;
import com.example.bonvoath.tms.R;

import java.util.ArrayList;

public class DialogOrderGoToAdapter extends RecyclerView.Adapter<DialogOrderGoToAdapter.ViewHolder> {

    private DialogOrderGoToAdapter.OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener{
        void OnItemClick(View view, int positionId);
    }
    private static final String TAG = "DialogOrderGoToAdapter";
    private Context mContext;

    public DialogOrderGoToAdapter(Context context){
        mContext = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_order_list_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int posId = holder.getAdapterPosition();
                if(mOnItemClickListener != null)
                    mOnItemClickListener.OnItemClick(v, posId);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderMaster order = DataSet.OrderMasters.get(position);
        holder.txt_order_num.setText(order.getOrderNumber());
        holder.txt_order_date.setText(order.getOrderDate());
        holder.txt_order_address.setText(order.getAddress());
    }

    @Override
    public int getItemCount() {
        return DataSet.OrderMasters.size();
    }

    public void setOnItemClickListener(DialogOrderGoToAdapter.OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_order_num;
        TextView txt_order_date;
        TextView txt_order_address;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_order_num = itemView.findViewById(R.id.txtTag);
            txt_order_date = itemView.findViewById(R.id.txtRemark);
            //txt_order_address= itemView.findViewById(R.id.txt_order_address);
        }
    }
}