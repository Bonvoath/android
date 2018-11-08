package com.example.bonvoath.tms.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bonvoath.tms.Entities.GoogleAddressResult;
import com.example.bonvoath.tms.R;

import java.util.List;

public class GoogleSearchAddressAdapter extends RecyclerView.Adapter<GoogleSearchAddressAdapter.ViewHolder>{
    public interface OnGoogleSearchListener{
        void onSelect(View v, GoogleAddressResult sender);
    }
    private OnGoogleSearchListener mOnGoogleSearchListener;

    private List<GoogleAddressResult> data;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_google_search_address_item, parent, false);

        return new GoogleSearchAddressAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        final GoogleAddressResult result = data.get(i);
        holder.txtLat.setText(result.getLat());
        holder.txtLong.setText(result.getLng());
        if(!result.getArea1().equals("null"))
            holder.txtArea1.setText(result.getArea1());
        if(!result.getArea2().equals("null"))
            holder.txtArea2.setText(result.getArea2());
        if(!result.getSubLocality().equals("null"))
            holder.txtSubLocality.setText(result.getSubLocality());
        if(!result.getLocality().equals("null"))
            holder.txtLocality.setText(result.getLocality());
        holder.txtCountry.setText(result.getCountry());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<GoogleAddressResult> googleAddressResultList){
        this.data = googleAddressResultList;
    }

    public void setOnGoogleSearchListener(OnGoogleSearchListener onGoogleSearchListener){
        mOnGoogleSearchListener = onGoogleSearchListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtLat;
        TextView txtLong;
        TextView txtArea1;
        TextView txtArea2;
        TextView txtCountry;
        TextView txtLocality;
        TextView txtSubLocality;
        Button btnSelect;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLat = itemView.findViewById(R.id.txt_lat);
            txtLong = itemView.findViewById(R.id.txt_lng);
            txtArea1 = itemView.findViewById(R.id.txt_area1);
            txtArea2 = itemView.findViewById(R.id.txt_area2);
            txtLocality = itemView.findViewById(R.id.txt_locality);
            txtSubLocality = itemView.findViewById(R.id.txt_sub_locality);
            txtCountry = itemView.findViewById(R.id.txt_country);
            btnSelect = itemView.findViewById(R.id.btn_select);
            btnSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnGoogleSearchListener.onSelect(v, data.get(getAdapterPosition()));
                }
            });
        }
    }
}
