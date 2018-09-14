package com.khl.bonvoath.kpc.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.khl.bonvoath.kpc.R;
import com.khl.bonvoath.kpc.entities.Penalty;

import java.util.List;

public class PenaltyRecycleAdapter extends RecyclerView.Adapter<PenaltyRecycleAdapter.Holder> {
    private List<Penalty> data;
    @NonNull
    @Override
    public PenaltyRecycleAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.motivation_item_view, parent, false);

        return new PenaltyRecycleAdapter.Holder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull PenaltyRecycleAdapter.Holder holder, int position) {
        Penalty penalty = data.get(position);
        holder.certificateTextView.setText(penalty.getLetter());
        holder.dateTextView.setText(penalty.getDate());
        holder.levelTextView.setText(penalty.getImg());
        holder.orgTextView.setText(penalty.getOrg());
        holder.meaningTextView.setText(penalty.getMeaning());
    }

    public void setData(List<Penalty> penalties){
        data = penalties;
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
