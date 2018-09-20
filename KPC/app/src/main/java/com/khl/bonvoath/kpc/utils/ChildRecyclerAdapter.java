package com.khl.bonvoath.kpc.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.khl.bonvoath.kpc.R;
import com.khl.bonvoath.kpc.entities.Child;

import java.util.List;

public class ChildRecyclerAdapter extends RecyclerView.Adapter<ChildRecyclerAdapter.Holder> {
    private List<Child> data;
    @NonNull
    @Override
    public ChildRecyclerAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.children_list_item_view, parent, false);

        return new ChildRecyclerAdapter.Holder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildRecyclerAdapter.Holder holder, int position) {
        Child child = data.get(position);
        holder.nameTextView.setText(child.getName());
        holder.dateTextView.setText(child.getDob());
        holder.sexTextView.setText(child.getSex());
        holder.jobTextView.setText(child.getJob());
    }

    public void setData(List<Child> children){
        data = children;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView nameTextView;
        TextView dateTextView;
        TextView sexTextView;
        TextView jobTextView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.txtName);
            dateTextView = itemView.findViewById(R.id.txtDob);
            sexTextView = itemView.findViewById(R.id.txtSex);
            jobTextView = itemView.findViewById(R.id.txtJob);
        }
    }
}
