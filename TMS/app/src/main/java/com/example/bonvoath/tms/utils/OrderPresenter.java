package com.example.bonvoath.tms.utils;

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

    private Adapter adapter;

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
        List<OrderMaster> list = new ArrayList<>();
        for (OrderMaster u : all) {
            if(u.getRemark() != null && !u.getRemark().equals("")) {
                String hash = "#" + query;
                if (u.getRemark().toLowerCase().contains(hash)) {
                    u.setTag(hash);
                    list.add(u);
                }
            }
        }
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }

    class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

        private List<OrderMaster> data;

        class Holder extends RecyclerView.ViewHolder {
            View root;
            TextView txtTag;
            TextView txtRemark;
            Holder(@NonNull View itemView) {
                super(itemView);
                root = itemView;
                txtRemark = itemView.findViewById(R.id.txtRemark);
                txtTag = itemView.findViewById(R.id.txtTag);
            }
        }

        public void setData(List<OrderMaster> data) {
            this.data = data;
        }

        @Override
        public int getItemCount() {
            return (isEmpty()) ? 1 : data.size();
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@Nullable ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(getContext()).inflate(R.layout.content_order_list_item, parent, false));
        }

        private boolean isEmpty() {
            return data == null || data.isEmpty();
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {
            if(isEmpty()){
                return;
            }

            final OrderMaster order = data.get(position);
            holder.txtTag.setText(getHashTag(order));
            holder.txtRemark.setText(order.getRemark());
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dispatchClick(order);
                }
            });
        }

        private String getHashTag(OrderMaster order){
            String remark = order.getRemark();
            String query = order.getTag();
            String ret = "";
            if(!remark.equals("") && query != null) {
                String[] words = remark.split(" ");
                for (int i = 0; i < words.length ; i++) {
                    String word = words[i].toLowerCase();
                    if(word.contains(query)){
                        ret = word;
                    }
                }
            }

            return ret;
        }
    }
}