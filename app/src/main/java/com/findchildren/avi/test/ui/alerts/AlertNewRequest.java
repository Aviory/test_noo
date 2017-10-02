package com.findchildren.avi.test.ui.alerts;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.findchildren.avi.test.Const;
import com.findchildren.avi.test.R;
import com.findchildren.avi.test.api.ApiManager;
import com.findchildren.avi.test.api.ApiService;
import com.findchildren.avi.test.models.Request;
import com.findchildren.avi.test.models.RequestComment;
import com.findchildren.avi.test.ui.MainActivity;
import com.findchildren.avi.test.ui.fragments.RecycleCardsFragment;
import com.findchildren.avi.test.utils.LogUtil;
import com.findchildren.avi.test.utils.UiUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Avi on 01.10.2017.
 */

public class AlertNewRequest extends Fragment implements View.OnClickListener {

    private Button mBtnClose;
    private Button mBtnSendMsg;
    private EditText mTextname;
    private Button mTextBirth;
    private Spinner mTextSex;
    private EditText mTextRegion;
    private EditText mSituationDescription;
    private EditText mChildDescription;
    private Button mTextLossDate;
    private Button mTextLossTime;
    private Button mTextToLossTime;
    private Spinner mTextStatus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alert_new_request, container, false);
        mBtnClose =  v.findViewById(R.id.alert_btn_close);
        mBtnSendMsg = v.findViewById(R.id.btn_send_request);
        mTextname = v.findViewById(R.id.alert_request_txt_name);
        mTextBirth = v.findViewById(R.id.alert_request_txt_birth);
        mTextSex = v.findViewById(R.id.alert_request_txt_sex);
        mTextRegion = v.findViewById(R.id.alert_request_txt_region);
        mSituationDescription = v.findViewById(R.id.alert_request_txt_description);
        mChildDescription = v.findViewById(R.id.alert_request_txt_child_description);
        mTextLossDate = v.findViewById(R.id.alert_request_txt_date_od_loss);
        mTextLossTime = v.findViewById(R.id.alert_request_txt_time_od_loss);
        mTextToLossTime = v.findViewById(R.id.alert_request_txt_to_time_od_loss);
        mTextStatus = v.findViewById(R.id.alert_request_txt_status);

        mTextLossDate.setOnClickListener(this);
        mTextToLossTime.setOnClickListener(this);
        mTextBirth.setOnClickListener(this);
        mTextLossTime.setOnClickListener(this);
        mBtnClose.setOnClickListener(this);
        mBtnSendMsg.setOnClickListener(this);
        return v;
    }

    private void sendMsg(){
        Request newRequest = new Request();
        newRequest.setName(mTextname.getText().toString());
        newRequest.setDateOfBirth(mTextBirth.getText().toString());
        newRequest.setGender(mTextSex.getSelectedItem().toString());
        newRequest.setRegion(mTextRegion.getText().toString());
        newRequest.setSituationDescription(mSituationDescription.getText().toString());
        newRequest.setChildDescription(mChildDescription.getText().toString());
        newRequest.setTimeOfLoss(mTextLossDate.getText()+" "+mTextLossTime.getText()+"+"+mTextToLossTime.getText());
        newRequest.setStatus(mTextStatus.getSelectedItem().toString());

        ApiService apiService = ApiManager.getApi().create(ApiService.class);
//        long id = getArguments().getLong(Const.CURRENT_USER_ID);

        apiService.sendNewRequest(newRequest).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                LogUtil.log("TAG", "onResponse: "+response.message());
                LogUtil.log("TAG", "onResponse body: "+response.body());
                UiUtil.showToast(getActivity(), "message successful sent");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LogUtil.log("TAG", "onFailure: "+t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.alert_btn_close:
                MainActivity main = (MainActivity)getActivity();
                main.addFrag(RecycleCardsFragment.getInstance(),Const.FRAGMENT_MAIN_TAG);
                break;
            case R.id.btn_send_request:
                sendMsg();
                break;
            case R.id.alert_request_txt_birth:
            case R.id.alert_request_txt_date_od_loss:
                AddDataPiker dateDialogDate = new AddDataPiker();
                dateDialogDate.show(getFragmentManager(), Const.DATE_PIKER, (Button) view);
                break;
            case R.id.alert_request_txt_time_od_loss:
                AddTimePiker dateDialogTime = new AddTimePiker();
                dateDialogTime.show(getFragmentManager(), Const.DATE_PIKER, (Button) view);
                break;
            case R.id.alert_request_txt_to_time_od_loss:
                AddTimePiker dateDialogToTime = new AddTimePiker();
                dateDialogToTime.show(getFragmentManager(), Const.DATE_PIKER, (Button) view);
                break;
        }
    }
}
