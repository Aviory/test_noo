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
import android.widget.ImageView;

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
 * Created by avi on 28.09.17.
 */

public class AlertAddComent extends DialogFragment implements View.OnClickListener {
    private Button mBtnClose;
    private Button mBtnSendMsg;
    private EditText mTextBody;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alert_add_comment, container, false);
        mBtnClose =  v.findViewById(R.id.btn_close);
        mBtnSendMsg = v.findViewById(R.id.btn_send_comment);
        mTextBody = v.findViewById(R.id.et_body_comment);

        mBtnClose.setOnClickListener(this);
        mBtnSendMsg.setOnClickListener(this);
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

    private void sendMsg(){
        ApiService apiService = ApiManager.getApi().create(ApiService.class);
        long id = getArguments().getLong(Const.CURRENT_USER_ID);
        String txt = mTextBody.getText().toString();
        LogUtil.log("TAG", "id: "+id+" txt"+txt);
        apiService.sendMsg(id,txt).enqueue(new Callback<RequestComment>() {
            @Override
            public void onResponse(Call<RequestComment> call, Response<RequestComment> response) {
                LogUtil.log("TAG", "onFailure: "+response.message());
                LogUtil.log("TAG", "onFailure: "+response.body());
                if(response.code()>=200 && response.code()<=208){
                    mTextBody.setText("");
                    UiUtil.showToast(getActivity(), "request successful remove");
                    dismiss();
                }
            }

            @Override
            public void onFailure(Call<RequestComment> call, Throwable t) {
                LogUtil.log("TAG", "onFailure: "+t.getMessage());
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_close:
                dismiss();
                break;
            case R.id.btn_send_comment:
                sendMsg();
                break;
        }
    }
}
