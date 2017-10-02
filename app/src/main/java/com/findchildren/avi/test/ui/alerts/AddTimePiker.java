package com.findchildren.avi.test.ui.alerts;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.findchildren.avi.test.Const;
import com.findchildren.avi.test.R;

import java.util.Calendar;

/**
 * Created by Avi on 02.10.2017.
 */

public class AddTimePiker extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    private TextView textView;
    private String currentTime;
    public void show(FragmentManager manager, String tag, Button textView) {
        super.show(manager, tag);
        this.textView = textView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog picker = new TimePickerDialog(getContext(),this,0,0,true);
        picker.setTitle(getResources().getString(R.string.choose_time));

        return picker;
    }
    @Override
    public void onStart() {
        super.onStart();
        Button nButton =  ((TimePickerDialog) getDialog())
                .getButton(DialogInterface.BUTTON_POSITIVE);
        nButton.setText(getResources().getString(R.string.btn_ok));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        textView.setText(timePicker.getCurrentHour()+":" + timePicker.getCurrentMinute()+":00");
    }
}