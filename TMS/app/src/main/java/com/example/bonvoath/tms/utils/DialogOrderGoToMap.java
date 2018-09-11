package com.example.bonvoath.tms.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bonvoath.tms.Entities.OrderMaster;
import com.example.bonvoath.tms.R;
import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;

public class DialogOrderGoToMap extends DialogFragment implements
        DialogOrderGoToAdapter.OnItemClickListener {
    public interface OnDialogItemClick{
        void OnItemClick(View view, int positionId);
    }
    private OnDialogItemClick mDialogItemClick;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.dialog_order_list_item, container, false);
        uRecyclerView = mView.findViewById(R.id.list_order_item);
        DialogOrderGoToAdapter adapter = new DialogOrderGoToAdapter(getActivity());
        uRecyclerView.setAdapter(adapter);
        uRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setOnItemClickListener(this);
        btnClose = mView.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getDialog() != null)
            if(getDialog().getWindow() != null)
                getDialog().getWindow().setWindowAnimations(R.style.DialogMapInfo);
    }

    @Override
    public void OnItemClick(View view, int positionId) {
        getDialog().dismiss();
        if(mDialogItemClick != null)
            mDialogItemClick.OnItemClick(view, positionId);
    }

    public void setOnDialogItemClick(DialogOrderGoToMap.OnDialogItemClick onDialogOrderGoToMap){
        mDialogItemClick = onDialogOrderGoToMap;
    }

    RecyclerView uRecyclerView;
    ImageButton btnClose;
}
