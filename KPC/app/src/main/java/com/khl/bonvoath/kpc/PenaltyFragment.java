package com.khl.bonvoath.kpc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PenaltyFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_penalty, container, false);
        initializeComponent(mView);

        return mView;
    }

    private void initializeComponent(View view){

    }
}
