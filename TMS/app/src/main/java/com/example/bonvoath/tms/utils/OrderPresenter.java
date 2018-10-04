package com.example.bonvoath.tms.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bonvoath.tms.Entities.OrderMaster;
import com.example.bonvoath.tms.Entities.OrderSearch;
import com.example.bonvoath.tms.R;
import com.otaliastudios.autocomplete.RecyclerViewPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderPresenter extends RecyclerViewPresenter<OrderSearch> {

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
        String url = TMSLib.getUrl(getContext(), R.string.order_get_by_tag);
        final String tagQuery = (query != null? query.toString():"");
        Map<String, Object> params = new HashMap<>();
        params.put("Tag", tagQuery);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    boolean isError = response.getBoolean("IsError");
                    if(!isError){
                        List<OrderSearch> list = new ArrayList<>();
                        JSONArray data = response.getJSONArray("Data");
                        for(int i= 0; i<data.length(); i++){
                            JSONObject obj = (JSONObject) data.get(i);
                            double lat = obj.getDouble("Lat");
                            double lng = obj.getDouble("Long");
                            String name = obj.getString("Name");
                            String price = obj.getString("Price");
                            String num = obj.getString("OrderNum");
                            String date = obj.getString("OrderDate");
                            String address = obj.getString("Address");
                            String remark =(obj.isNull("Remark")?"":obj.getString("Remark"));
                            OrderMaster order = new OrderMaster(num, date, address);
                            order.setLat(lat);
                            order.setName(name);
                            order.setLong(lng);
                            order.setRemark(remark);
                            order.setPrice(price);

                            String tag = getHashTag(remark, tagQuery);
                            boolean isHas = false;
                            for (OrderSearch s: list) {
                                if(s.getTag().equals(tag)){
                                    s.add(1);
                                    isHas = true;
                                    s.addOrders(order);
                                    break;
                                }
                            }
                            if(!isHas){
                                OrderSearch search = new OrderSearch();
                                search.setTag(tag);
                                search.setCount(1);
                                search.addOrders(order);
                                list.add(search);
                            }
                        }

                        Collections.sort(list, new Comparator<OrderSearch>() {
                            @Override
                            public int compare(OrderSearch o1, OrderSearch o2) {
                                return o1.getCount() - o2.getCount();
                            }
                        });
                        adapter.setData(list);
                        adapter.notifyDataSetChanged();
                    }
                }catch (JSONException e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(getContext()).add(request);
    }

    private String getHashTag(String remark, String query){
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

    class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
        private List<OrderSearch> data;

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

        public void setData(List<OrderSearch> data) {
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

            final OrderSearch order = data.get(position);
            holder.txtTag.setText(order.getTag());
            holder.txtRemark.setText(order.getCount() + " post");
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dispatchClick(order);
                }
            });
        }
    }
}