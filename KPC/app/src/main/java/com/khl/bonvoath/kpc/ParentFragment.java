package com.khl.bonvoath.kpc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khl.bonvoath.kpc.entities.Penalty;
import com.khl.bonvoath.kpc.utils.PenaltyRecycleAdapter;

import java.util.ArrayList;
import java.util.List;

public class ParentFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_parent, container, false);
        initializeComponent(mView);

        return mView;
    }

    private void initializeComponent(View view){
        List<Penalty> data = new ArrayList<>();
        data.add(new Penalty("អនុក្រ.តត ៣៨៦","៣១-០៣-២០១៦","រាជរដ្ឋាភិបាល","មេដាយមាស","ឥស្សយសការងារ"));
        data.add(new Penalty("អនុក្រ.តត ៣៨៦","៣១-០៣-២០១៦","រាជរដ្ឋាភិបាល","មេដាយមាស","ឥស្សយសការងារ"));
        data.add(new Penalty("អនុក្រ.តត ៣៨៦","៣១-០៣-២០១៦","រាជរដ្ឋាភិបាល","មេដាយមាស","ឥស្សយសការងារ"));
        data.add(new Penalty("អនុក្រ.តត ៣៨៦","៣១-០៣-២០១៦","រាជរដ្ឋាភិបាល","មេដាយមាស","ឥស្សយសការងារ"));
        motivationRecycleList = view.findViewById(R.id.penalty_recycle_list);
        motivationRecycleList.setNestedScrollingEnabled(false);
        PenaltyRecycleAdapter adapter = new PenaltyRecycleAdapter();
        adapter.setData(data);
        motivationRecycleList.setAdapter(adapter);
        motivationRecycleList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    RecyclerView motivationRecycleList;
}
