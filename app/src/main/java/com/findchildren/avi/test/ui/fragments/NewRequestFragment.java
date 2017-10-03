package com.findchildren.avi.test.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.findchildren.avi.test.Const;
import com.findchildren.avi.test.R;
import com.findchildren.avi.test.api.ApiManager;
import com.findchildren.avi.test.api.ApiService;
import com.findchildren.avi.test.models.Request;
import com.findchildren.avi.test.ui.MainActivity;
import com.findchildren.avi.test.ui.alerts.AddDataPiker;
import com.findchildren.avi.test.ui.alerts.AddTimePiker;
import com.findchildren.avi.test.utils.LogUtil;
import com.findchildren.avi.test.utils.UiUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Avi on 01.10.2017.
 */

public class NewRequestFragment extends Fragment implements View.OnClickListener {

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
    private Spinner mTextToLossTime;
    private Spinner mTextStatus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_request, container, false);
        mBtnClose =  v.findViewById(R.id.frg_btn_close);
        mBtnSendMsg = v.findViewById(R.id.btn_send_request);
        mTextname = v.findViewById(R.id.frg_request_txt_name);
        mTextBirth = v.findViewById(R.id.frg_request_txt_birth);
        mTextSex = v.findViewById(R.id.frg_request_txt_sex);
        mTextRegion = v.findViewById(R.id.frg_request_txt_region);
        mSituationDescription = v.findViewById(R.id.alert_request_txt_description);
        mChildDescription = v.findViewById(R.id.alert_request_txt_child_description);
        mTextLossDate = v.findViewById(R.id.frg_request_txt_date_od_loss);
        mTextLossTime = v.findViewById(R.id.frg_request_txt_time_od_loss);
        mTextToLossTime = v.findViewById(R.id.frg_request_txt_to_time_od_loss);
        mTextStatus = v.findViewById(R.id.frg_request_txt_status);

        mTextLossDate.setOnClickListener(this);
        mTextBirth.setOnClickListener(this);
        mTextLossTime.setOnClickListener(this);
        mBtnClose.setOnClickListener(this);
        mBtnSendMsg.setOnClickListener(this);
        return v;
    }

    private void sendMsg(){
        if(mTextBirth.getText().equals("ENTER DATE") || mTextLossTime.getText().equals("ENTER TIME") || mTextLossDate.getText().equals("ENTER DATE")){
            UiUtil.showToast(getActivity(), "enter date and time");
            return;
        }
        Request newRequest = new Request();
        newRequest.setName(mTextname.getText().toString());
        newRequest.setDateOfBirth(mTextBirth.getText().toString());
        newRequest.setGender(mTextSex.getSelectedItem().toString());
        newRequest.setRegion(mTextRegion.getText().toString());
        newRequest.setSituationDescription(mSituationDescription.getText().toString());
        newRequest.setChildDescription(mChildDescription.getText().toString());
        newRequest.setTimeOfLoss(mTextLossDate.getText()+" "+mTextLossTime.getText()+"+"+mTextToLossTime.getSelectedItem());
        newRequest.setStatus(mTextStatus.getSelectedItem().toString());

        ApiService apiService = ApiManager.getApi().create(ApiService.class);
//        long id = getArguments().getLong(Const.CURRENT_USER_ID);

        apiService.sendNewRequest(newRequest).enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                if(response.code()>=200 && response.code()<=208){
                    close();
                    UiUtil.showToast(getActivity(), "message successful sent");
                }
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                LogUtil.log("TAG", "onFailure: "+t.getMessage());
                UiUtil.showToast(getActivity(), t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_send_request:
                sendMsg();
                break;
            case R.id.frg_btn_close:
                close();
                break;
            case R.id.frg_request_txt_birth:
            case R.id.frg_request_txt_date_od_loss:
                AddDataPiker dateDialogDate = new AddDataPiker();
                dateDialogDate.show(getFragmentManager(), Const.DATE_PIKER, (Button) view);
                break;
            case R.id.frg_request_txt_time_od_loss:
                AddTimePiker dateDialogTime = new AddTimePiker();
                dateDialogTime.show(getFragmentManager(), Const.DATE_PIKER, (Button) view);
                break;
        }
    }

    private void close() {
        MainActivity main = (MainActivity)getActivity();
        main.addFrag(RecycleCardsFragment.getInstance(),Const.FRAGMENT_MAIN_TAG);
    }
}
