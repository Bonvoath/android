package com.example.bonvoath.tms.utils.swipes;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import com.example.bonvoath.tms.R;
import java.util.Calendar;

public class TimePickerDialogFragment extends SwipeAwayDialogFragment {
    Calendar calendar;
    public TimePickerDialogFragment(){
        calendar = Calendar.getInstance();
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(), hour, minute, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getDialog() != null)
            if(getDialog().getWindow() != null)
                getDialog().getWindow().setWindowAnimations(R.style.DialogMapInfo);
    }

    public void setHour(int hour){
        calendar.set(Calendar.HOUR_OF_DAY, hour);
    }

    public void setMinute(int minute){
        calendar.set(Calendar.MINUTE, minute);
    }
}
