package com.example.bonvoath.tms.utils.swipes;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import com.example.bonvoath.tms.R;
import java.util.Calendar;

public class DatePickerDialogFragment extends SwipeAwayDialogFragment {
    Calendar calendar;
    DatePickerDialog mDatePickerDialog;
    public DatePickerDialogFragment(){
        calendar = Calendar.getInstance();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        mDatePickerDialog =  new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);

        return mDatePickerDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getDialog() != null)
            if(getDialog().getWindow() != null)
                getDialog().getWindow().setWindowAnimations(R.style.DialogMapInfo);
    }

    public void setMonth(int month){
        calendar.set(Calendar.MONTH, month);
    }

    public void setYear(int year){
        calendar.set(Calendar.YEAR, year);
    }

    public void setDay(int day){
        calendar.set(Calendar.DAY_OF_MONTH, day);
    }
}
