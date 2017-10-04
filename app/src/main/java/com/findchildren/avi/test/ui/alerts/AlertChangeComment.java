package com.findchildren.avi.test.ui.alerts;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.findchildren.avi.test.Const;
import com.findchildren.avi.test.R;
import com.findchildren.avi.test.api.ApiManager;
import com.findchildren.avi.test.api.ApiService;
import com.findchildren.avi.test.models.RequestComment;
import com.findchildren.avi.test.utils.LogUtil;
import com.findchildren.avi.test.utils.UiUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public static AlertChangeComment getInstance() {
        AlertChangeComment mInstance = new AlertChangeComment();
        return mInstance;
    }

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
