package com.findchildren.avi.test.ui.alerts;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.widget.Button;
import android.widget.TextView;

import com.findchildren.avi.test.R;

import java.util.Calendar;

/**
 * Created by Avi on 02.10.2017.
 */

public class AddDataPiker extends DialogFragment
        implements DatePickerDialog.OnDateSetListener{
    private TextView textView;
    public void show(FragmentManager manager, String tag, Button textView) {
        super.show(manager, tag);
        this.textView = textView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        Dialog picker = new DatePickerDialog(getActivity(), this,
                year, month, day);
        picker.setTitle(getResources().getString(R.string.choose_date));

        return picker;
    }
    @Override
    public void onStart() {
        super.onStart();
        Button nButton =  ((DatePickerDialog) getDialog())
                .getButton(DialogInterface.BUTTON_POSITIVE);
        nButton.setText(getResources().getString(R.string.btn_ok));
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year,
                          int month, int day) {
        textView.setText(year + "-" + month + "-" + day);
    }
}