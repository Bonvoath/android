package com.khl.bonvoath.kpc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.khl.bonvoath.kpc.entities.GridMenu;
import com.khl.bonvoath.kpc.utils.DataSet;
import com.khl.bonvoath.kpc.utils.HomeGridAdapter;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_home, container, false);
        initializeComponent(mView);

        return mView;
    }

    private void initializeComponent(View view){
        DataSet.menus = new ArrayList<>();
        DataSet.menus.add(new GridMenu(R.drawable.ic_home_cc0000_24dp, getResources().getString(R.string.label_kh_home)));
        DataSet.menus.add(new GridMenu(R.drawable.ic_people_outline_cc0000_24dp, getResources().getString(R.string.profile)));
        DataSet.menus.add(new GridMenu(R.drawable.ic_graduation_cap_cc0000_24dp,getResources().getString(R.string.label_kh_education)));
        DataSet.menus.add(new GridMenu(R.drawable.ic_tasks_cc0000_24dp,getResources().getString(R.string.label_kh_experience)));
        DataSet.menus.add(new GridMenu(R.drawable.ic_gift_solid_cc0000_24dp,getResources().getString(R.string.label_motivation)));
        DataSet.menus.add(new GridMenu(R.drawable.ic_notifications_off_cc0000_24dp,getResources().getString(R.string.label_penalty)));
        DataSet.menus.add(new GridMenu(R.drawable.ic_group_add_cc0000_24dp,getResources().getString(R.string.label_couple)));
        DataSet.menus.add(new GridMenu(R.drawable.ic_child_solid_cc0000_24dp,getResources().getString(R.string.label_child)));
        DataSet.menus.add(new GridMenu(R.drawable.ic_people_outline_cc0000_24dp,getResources().getString(R.string.label_parent)));
        DataSet.menus.add(new GridMenu(R.drawable.ic_hearing_cc0000_24dp,getResources().getString(R.string.label_reference)));
        mGridView = view.findViewById(R.id.grid_view_home);
        mGridView.setVerticalScrollBarEnabled(false);
        mGridView.setAdapter(new HomeGridAdapter(getContext(), DataSet.menus));
    }

    GridView mGridView;
}
