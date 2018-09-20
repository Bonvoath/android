package com.khl.bonvoath.kpc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khl.bonvoath.kpc.entities.Child;
import com.khl.bonvoath.kpc.utils.ChildRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FamilyFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_family, container, false);
        initializeComponent(mView);

        return mView;
    }

    private void initializeComponent(View view){
        mRecyclerView = view.findViewById(R.id.family_child_list);
        mRecyclerView.setNestedScrollingEnabled(false);
        List<Child> children = new ArrayList<>();
        Child child = new Child("អ៊ឹង វិទូ","ប្រុស","02-04-2013","សិស្ស");
        children.add(child);
        children.add(child);
        children.add(child);
        children.add(child);
        children.add(child);
        children.add(child);
        children.add(child);
        children.add(child);
        children.add(child);
        children.add(child);
        children.add(child);
        children.add(child);

        ChildRecyclerAdapter adapter = new ChildRecyclerAdapter();
        adapter.setData(children);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    RecyclerView mRecyclerView;
}