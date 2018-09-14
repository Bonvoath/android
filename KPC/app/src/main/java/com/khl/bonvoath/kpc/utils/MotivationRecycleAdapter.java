package com.khl.bonvoath.kpc.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.khl.bonvoath.kpc.R;
import com.khl.bonvoath.kpc.entities.Motivation;

import java.util.List;

public class MotivationRecycleAdapter extends RecyclerView.Adapter<MotivationRecycleAdapter.Holder> {
    private List<Motivation> data;
    @NonNull
    @Override
    public MotivationRecycleAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.motivation_item_view, parent, false);

        return new MotivationRecycleAdapter.Holder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MotivationRecycleAdapter.Holder holder, int position) {
        Motivation motivation = data.get(position);
        holder.certificateTextView.setText(motivation.getLetter());
        holder.dateTextView.setText(motivation.getDate());
        holder.levelTextView.setText(motivation.getImg());
        holder.orgTextView.setText(motivation.getOrg());
        holder.meaningTextView.setText(motivation.getMeaning());
    }

    public void setData(List<Motivation> motivations){
        data = motivations;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView certificateTextView;
        TextView dateTextView;
        TextView levelTextView;
        TextView orgTextView;
        TextView meaningTextView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            certificateTextView = itemView.findViewById(R.id.txtCertificate);
            dateTextView = itemView.findViewById(R.id.txtDate);
            levelTextView = itemView.findViewById(R.id.txtLevel);
            orgTextView = itemView.findViewById(R.id.txtOrg);
            meaningTextView = itemView.findViewById(R.id.txtMeaning);
        }
    }
}
