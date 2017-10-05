package com.findchildren.avi.test.ui.alerts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.findchildren.avi.test.Const;
import com.findchildren.avi.test.R;

/**
 * Created by Avi on 03.10.2017.
 */

public class AlertChangeComment extends DialogFragment {
    private View.OnClickListener onClick;


    public void setOnClick(View.OnClickListener onClick) {
        this.onClick = onClick;
    }

    private Button mBtnRemove;
    private Button mBtnUpdate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alert_change_comment, container, false);
        mBtnRemove =  v.findViewById(R.id.btn_remove);
        mBtnUpdate = v.findViewById(R.id.btn_update);

        mBtnRemove.setOnClickListener(onClick);
        mBtnUpdate.setOnClickListener(onClick);
        return v;
    }
}
