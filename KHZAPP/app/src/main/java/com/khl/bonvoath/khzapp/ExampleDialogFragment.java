package com.khl.bonvoath.khzapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class ExampleDialogFragment extends DialogFragment {
    public static ExampleDialogFragment newInstance(int title) {
        ExampleDialogFragment f = new ExampleDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        f.setArguments(args);
        return f;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle("Title")
                .setMessage("Message")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(true)
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancel", null)
                .create();
    }
}
