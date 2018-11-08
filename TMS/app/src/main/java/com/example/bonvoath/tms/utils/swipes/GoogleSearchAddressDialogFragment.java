package com.example.bonvoath.tms.utils.swipes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bonvoath.tms.Entities.GoogleAddressResult;
import com.example.bonvoath.tms.R;
import com.example.bonvoath.tms.utils.GoogleSearchAddressAdapter;

import java.util.ArrayList;
import java.util.List;

public class GoogleSearchAddressDialogFragment extends SwipeAwayDialogFragment implements
        View.OnClickListener, GoogleSearchAddressAdapter.OnGoogleSearchListener {
    public interface OnGoogleSearchDialogFragmentListener{
        void onSelectGoogleResult(View v, GoogleAddressResult sender);
    }
    OnGoogleSearchDialogFragmentListener mOnGoogleSearchDialogFragmentListener;
    List<GoogleAddressResult> data = new ArrayList<>();
    ImageButton btnClose;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.layout_google_search_address, container, false);
        RecyclerView recycler = mView.findViewById(R.id.recycle_google_search_address_result);
        GoogleSearchAddressAdapter adapter = new GoogleSearchAddressAdapter();
        adapter.setOnGoogleSearchListener(this);
        adapter.setData(data);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        btnClose = mView.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(this);

        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getDialog() != null)
            if(getDialog().getWindow() != null)
                getDialog().getWindow().setWindowAnimations(R.style.DialogMapInfo);
    }

    public void addItem(GoogleAddressResult item){
        data.add(item);

    }

    public void setOnGoogleSearchDialogFragmentListener(OnGoogleSearchDialogFragmentListener listener){
        mOnGoogleSearchDialogFragmentListener = listener;
    }

    public static GoogleSearchAddressDialogFragment instance(String title) {
        GoogleSearchAddressDialogFragment f = new GoogleSearchAddressDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("type", title);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btnClose) {
            dismiss();
        }
    }

    @Override
    public void onSelect(View v, GoogleAddressResult sender) {
        mOnGoogleSearchDialogFragmentListener.onSelectGoogleResult(v, sender);
        dismiss();
    }
}
