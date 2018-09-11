package com.khl.bonvoath.sample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bonvoath.tms.Entities.DataSet;
import com.example.bonvoath.tms.Entities.OrderMaster;
import com.example.bonvoath.tms.R;
import com.otaliastudios.autocomplete.RecyclerViewPresenter;

import java.util.ArrayList;
import java.util.List;

public class OrderPresenter extends RecyclerViewPresenter<OrderMaster> {

    Adapter adapter;

    public OrderPresenter(Context context) {
        super(context);
    }

    @Override
    protected PopupDimensions getPopupDimensions() {
        PopupDimensions dims = new PopupDimensions();
        dims.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        return dims;
    }

    @Override
    protected RecyclerView.Adapter instantiateAdapter() {
        adapter = new Adapter();
        return adapter;
    }

    @Override
    protected void onQuery(@Nullable CharSequence query) {
        List<OrderMaster> all = DataSet.OrderMasters;
        if (TextUtils.isEmpty(query)) {
            adapter.setData(all);
        } else {
            query = query.toString().toLowerCase();
            List<OrderMaster> list = new ArrayList<>();
            for (OrderMaster u : all) {
                if (u.getRemark().toLowerCase().contains(query)) {
                    list.add(u);
                }
            }
            adapter.setData(list);
            Log.e("UserPresenter", "found "+list.size()+" users for query "+query);
        }
        adapter.notifyDataSetChanged();
    }

    class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

        private List<OrderMaster> data;

        class Holder extends RecyclerView.ViewHolder {
            View root;
            TextView txt_order_num;
            TextView txt_order_date;
            TextView txt_order_address;
            Holder(@NonNull View itemView) {
                super(itemView);
                root = itemView;
                txt_order_num = itemView.findViewById(R.id.txt_order_num);
                txt_order_date = itemView.findViewById(R.id.txt_order_date);
                txt_order_address= itemView.findViewById(R.id.txt_order_address);
            }
        }

        public void setData(List<OrderMaster> data) {
            this.data = data;
        }

        @Override
        public int getItemCount() {
            return (isEmpty()) ? 1 : data.size();
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(getContext()).inflate(R.layout.content_order_list_item, parent, false));
        }

        private boolean isEmpty() {
            return data == null || data.isEmpty();
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            if (isEmpty()) {
                holder.txt_order_num.setText("No order here!");
                holder.txt_order_date.setText("Sorry!");
                holder.txt_order_address.setText("Please try again.");
                holder.root.setOnClickListener(null);
                return;
            }
            final OrderMaster order = data.get(position);
            holder.txt_order_num.setText(order.getOrderNumber());
            holder.txt_order_date.setText(order.getOrderDate());
            holder.txt_order_address.setText(order.getRemark());
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dispatchClick(order);
                }
            });
        }
    }
}