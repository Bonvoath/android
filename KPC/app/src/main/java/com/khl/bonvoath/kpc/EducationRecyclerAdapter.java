package com.khl.bonvoath.kpc;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.khl.bonvoath.kpc.entities.Education;

import java.util.List;

public class EducationRecyclerAdapter extends RecyclerView.Adapter<EducationRecyclerAdapter.Holder> {
    List<Education> data;
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.education_item_view, parent, false);

        return new Holder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Education edu = data.get(position);
        holder.dateTextView.setText(edu.getFromDate() +" - "+ edu.getToDate());
    }

    public void setData(List<Education> educations){
        data = educations;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView dateTextView;
        TextView levelTextView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.txtDate);
            levelTextView = itemView.findViewById(R.id.txtLevel);
        }
    }
}
