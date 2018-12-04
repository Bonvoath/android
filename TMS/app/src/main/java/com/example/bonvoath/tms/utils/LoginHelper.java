package com.example.bonvoath.tms.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginHelper {
    public static String getUserId(Context context){
        SharedPreferences mSharedPreferences = context.getApplicationContext().getSharedPreferences("Auth", context.MODE_PRIVATE);
        return  mSharedPreferences.getString("UserId", "");
    }

    public static String getTruckNumber(Context context){
        SharedPreferences mSharedPreferences = context.getApplicationContext().getSharedPreferences("Auth", context.MODE_PRIVATE);
        return  mSharedPreferences.getString("TruckNumber", "");
    }
}
