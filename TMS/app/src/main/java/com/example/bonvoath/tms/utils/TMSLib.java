package com.example.bonvoath.tms.utils;

import android.content.Context;

import com.example.bonvoath.tms.R;

public class TMSLib {
    public static String getUrl(Context context, int path){
        String baseUrl = context.getResources().getString(R.string.server_address);
        String action = context.getResources().getString(path);

        return baseUrl + action;
    }
}
