package com.khl.bonvoath.kpc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khl.bonvoath.kpc.entities.Education;

import java.util.ArrayList;
import java.util.List;

public class EducationFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_education, container, false);
        initializeComponent(view);

        return view;
    }

    private void initializeComponent(View view){
        List<Education> edus = new ArrayList<>();
        Education edu1 = new Education();
        edu1.setFromDate("2017");
        edu1.setToDate("2018");
        edus.add(edu1);
        Education edu2 = new Education();
        edu2.setFromDate("2017");
        edu2.setToDate("2018");
        edus.add(edu2);
        edus.add(edu2);
        edus.add(edu2);
        edus.add(edu2);
        edus.add(edu2);
        edus.add(edu2);
        edus.add(edu2);
        edus.add(edu2);
        edus.add(edu2);
        edus.add(edu2);
        edus.add(edu2);
        edus.add(edu2);
        edus.add(edu2);
        edus.add(edu2);

        eduRecyclerView1 = view.findViewById(R.id.edu_list_type_1);
        eduRecyclerView1.setNestedScrollingEnabled(false);
        EducationRecyclerAdapter adapter = new EducationRecyclerAdapter();
        adapter.setData(edus);
        eduRecyclerView1.setAdapter(adapter);
        eduRecyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));

        eduRecyclerView2 = view.findViewById(R.id.edu_list_type_2);
        eduRecyclerView2.setNestedScrollingEnabled(false);
        EducationRecyclerAdapter adapter1 = new EducationRecyclerAdapter();
        adapter1.setData(edus);
        eduRecyclerView2.setAdapter(adapter1);
        eduRecyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

        eduRecyclerView3 = view.findViewById(R.id.edu_list_type_3);
        eduRecyclerView3.setNestedScrollingEnabled(false);
        EducationRecyclerAdapter adapter2 = new EducationRecyclerAdapter();
        adapter2.setData(edus);
        eduRecyclerView3.setAdapter(adapter2);
        eduRecyclerView3.setLayoutManager(new LinearLayoutManager(getContext()));

        eduRecyclerView4 = view.findViewById(R.id.edu_list_type_4);
        eduRecyclerView4.setNestedScrollingEnabled(false);
        EducationRecyclerAdapter adapter3 = new EducationRecyclerAdapter();
        adapter3.setData(edus);
        eduRecyclerView4.setAdapter(adapter3);
        eduRecyclerView4.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    RecyclerView eduRecyclerView1;
    RecyclerView eduRecyclerView2;
    RecyclerView eduRecyclerView3;
    RecyclerView eduRecyclerView4;
}
