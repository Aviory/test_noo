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

import com.findchildren.avi.test.R;

/**
 * Created by avi on 28.09.17.
 */

public class AlertAddComent extends DialogFragment implements View.OnClickListener{
    private EditText mTextBody;
    private CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack{
        void alertOnClick(String msg);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alert_add_comment, container, false);
        Button mBtnClose =  v.findViewById(R.id.btn_close);
        Button mBtnSendMsg = v.findViewById(R.id.btn_send_comment);
        mTextBody = v.findViewById(R.id.et_body_comment);

        mBtnSendMsg.setOnClickListener(this);
        mBtnClose.setOnClickListener(this);
        return v;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_send_comment:
                callBack.alertOnClick(mTextBody.getText().toString());
                break;
            case R.id.btn_close:
                dismiss();
                break;
        }
    }
}
