package com.example.bonvoath.tms;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import com.example.bonvoath.tms.utils.swipes.DatePickerDialogFragment;
import com.example.bonvoath.tms.utils.swipes.TimePickerDialogFragment;

import java.text.DecimalFormat;
import java.util.Calendar;

public class CreateOrderActivity extends AppCompatActivity implements View.OnClickListener,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener{
    Toolbar toolbar;
    EditText txtDeliveryDate;
    EditText txtDeliveryTime;
    DatePickerDialogFragment date;
    TimePickerDialogFragment time;
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        initializeComponent();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        txtDeliveryDate.setText(getDeliveryDate());
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        txtDeliveryTime.setText(getTime());
    }

    private void initializeComponent(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(R.string.create_new_order);
        }

        calendar = Calendar.getInstance();
        txtDeliveryDate = findViewById(R.id.txt_delivery_date);
        txtDeliveryDate.setText(getDeliveryDate());
        txtDeliveryTime = findViewById(R.id.txt_delivery_time);
        txtDeliveryTime.setText(getTime());
        time = new TimePickerDialogFragment();
        date = new DatePickerDialogFragment();


        ImageButton btnDate = findViewById(R.id.btnDate);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date.setMonth(calendar.get(Calendar.MONTH));
                date.setDay(calendar.get(Calendar.DAY_OF_MONTH));
                date.setYear(calendar.get(Calendar.YEAR));
                date.show(getFragmentManager(), getClass().getName());
            }
        });

        ImageButton btnTime = findViewById(R.id.btnTime);
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time.setHour(calendar.get(Calendar.HOUR_OF_DAY));
                time.setMinute(calendar.get(Calendar.MINUTE));
                time.show(getFragmentManager(), getClass().getName());
            }
        });
    }

    private String getDeliveryDate(){
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        return  month + "/" + day + "/" + year;
    }

    private String getTime(){
        String hour = new DecimalFormat("00").format(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = new DecimalFormat("00").format(calendar.get(Calendar.MINUTE));

        return  hour + ":" + minute;
    }
}
