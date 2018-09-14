package com.khl.bonvoath.kpc.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.khl.bonvoath.kpc.R;
import com.khl.bonvoath.kpc.entities.Experience;

import java.util.List;

public class ExperienceRecycleAdapter extends RecyclerView.Adapter<ExperienceRecycleAdapter.Holder> {
    private List<Experience> data;
    @NonNull
    @Override
    public ExperienceRecycleAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.experience_item_view, parent, false);

        return new ExperienceRecycleAdapter.Holder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExperienceRecycleAdapter.Holder holder, int position) {
        Experience exp = data.get(position);
        holder.dateTextView.setText(exp.getFrom() + " - " + exp.getTo());
        holder.levelTextView.setText(exp.getJob());
        holder.orgTextView.setText(exp.getOrg());
    }

    public void setData(List<Experience> experience){
        data = experience;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView dateTextView;
        TextView levelTextView;
        TextView orgTextView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.txtDate);
            levelTextView = itemView.findViewById(R.id.txtLevel);
            orgTextView = itemView.findViewById(R.id.txtOrg);
        }
    }
}
