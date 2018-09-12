package com.khl.bonvoath.kpc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khl.bonvoath.kpc.entities.Education;
import com.khl.bonvoath.kpc.entities.Experience;
import com.khl.bonvoath.kpc.utils.ExperienceRecycleAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExperienceFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_experience, container, false);
        initializeComponent(mView);

        return mView;
    }

    private void initializeComponent(View view){
        List<Experience> exps = new ArrayList<>();
        Experience exp = new Experience();
        exp.setFrom("2017");
        exp.setTo("2019");
        exp.setJob("ប្រធានអង្គភាពច្រកចេញចូលតែមួយ");
        exp.setOrg("អង្គភាពច្រកចេញចូលតែមួយសាលាខេត្តកំពង់ចាម");
        exps.add(exp);
        exps.add(exp);
        exps.add(exp);
        expRecyclerView1 = view.findViewById(R.id.exp_list_type_1);
        expRecyclerView1.setNestedScrollingEnabled(false);
        ExperienceRecycleAdapter adapter1 = new ExperienceRecycleAdapter();
        adapter1.setData(exps);
        expRecyclerView1.setAdapter(adapter1);
        expRecyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));

        expRecyclerView2 = view.findViewById(R.id.exp_list_type_2);
        expRecyclerView2.setNestedScrollingEnabled(false);
        ExperienceRecycleAdapter adapter2 = new ExperienceRecycleAdapter();
        adapter2.setData(exps);
        expRecyclerView2.setAdapter(adapter2);
        expRecyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

        expRecyclerView3 = view.findViewById(R.id.exp_list_type_3);
        expRecyclerView3.setNestedScrollingEnabled(false);
        ExperienceRecycleAdapter adapter3 = new ExperienceRecycleAdapter();
        adapter3.setData(exps);
        expRecyclerView3.setAdapter(adapter3);
        expRecyclerView3.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    RecyclerView expRecyclerView1;
    RecyclerView expRecyclerView2;
    RecyclerView expRecyclerView3;
}
