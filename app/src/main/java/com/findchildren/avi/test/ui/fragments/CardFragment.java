package com.findchildren.avi.test.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.findchildren.avi.test.Const;
import com.findchildren.avi.test.R;
import com.findchildren.avi.test.api.ApiManager;
import com.findchildren.avi.test.api.ApiService;
import com.findchildren.avi.test.models.Request;
import com.findchildren.avi.test.ui.MainActivity;
import com.findchildren.avi.test.utils.LogUtil;
import com.findchildren.avi.test.utils.UiUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Avi on 27.09.2017.
 */

public class CardFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.fragment_card_txt_id)
    protected TextView mTxtId;
    @BindView(R.id.fragment_card_txt_name)
    protected TextView mTxtname;
    @BindView(R.id.fragment_card_txt_date_of_birth)
    protected TextView mTxtDateOfBirth ;
    @BindView(R.id.fragment_card_txt_sex)
    protected TextView mTxtGender;
    @BindView(R.id.fragment_card_txt_region)
    protected TextView mTxtRegion;
    @BindView(R.id.fragment_card_txt_description)
    protected TextView mTxtDescription;
    @BindView(R.id.fragment_card_txt_time_od_loss)
    protected TextView mTxtTimeOfLoss;
    @BindView(R.id.fragment_card_txt_time_request)
    protected TextView mTxtTimeOfRequest;
    @BindView(R.id.fragment_card_txt_status)
    protected TextView mTxtStatus;
    @BindView(R.id.btn_close)
    protected Button mBtnClose;
    @BindView(R.id.btn_get_comments)
    protected Button mBtnGetComments;
    @BindView(R.id.btn_update_request)
    protected Button mBtnUpdate;
    @BindView(R.id.btn_delete)
    protected Button mBtnDelete;
    private Request request;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_card, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnClose.setOnClickListener(this);
        mBtnGetComments.setOnClickListener(this);
        mBtnUpdate.setOnClickListener(this);
        mBtnDelete.setOnClickListener(this);
        init();
    }

    public void setCard(Request request){
      this.request = request;
    }

    private void init(){
        if(request.getId()!=null)
            mTxtId.setText(String.valueOf(request.getId()));
        if(request.getName()!=null)
            mTxtname.setText(request.getName());
        if(request.getDateOfBirth()!=null)
            mTxtDateOfBirth.setText(request.getDateOfBirth());
        if(request.getGender()!=null)
            mTxtGender.setText(request.getGender());
        if(request.getRegion()!=null)
            mTxtRegion.setText(request.getRegion());
        if(request.getChildDescription()!=null)
            mTxtDescription.setText(request.getChildDescription());
        if(request.getTimeOfLoss()!=null)
            mTxtTimeOfLoss.setText(request.getTimeOfLoss());
        if(request.getTimeOfRequest()!=null)
            mTxtTimeOfRequest.setText(request.getTimeOfRequest());
        if(request.getStatus()!=null)
            mTxtStatus.setText(request.getStatus());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_close:
                onBackPressed();
                break;
            case R.id.btn_update_request:
                sendUpdate();
                break;
            case R.id.btn_delete:
                sendDelete();
                break;
            case R.id.btn_get_comments:
                RecycleCardsFragment main = (RecycleCardsFragment) getActivity().getSupportFragmentManager().findFragmentByTag(Const.FRAGMENT_MAIN_TAG);
                main.getFragmentComment(request.getId());
                break;
        }
    }

    private void sendDelete() {
        ApiService apiService = ApiManager.getApi().create(ApiService.class);
        apiService.deleteRequest(request.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<Response<ResponseBody>>() {
                    @Override
                    public void call(Response<ResponseBody> response) {
                        LogUtil.log("TAG", "onFailure: "+response.message());
                        LogUtil.log("TAG", "onFailure: "+response.body());
                        if(response.code()>=200 && response.code()<=208){
                            onBackPressed();
                            UiUtil.showToast(getActivity(), "request successful remove");
                        }
                    }
                });
    }

    private void sendUpdate() {
        UpdateRequestFragment alertUpRequest = new UpdateRequestFragment();
        alertUpRequest.setRequest(request);
        MainActivity main = (MainActivity) getActivity();
        main.addFrag(alertUpRequest,Const.FRAGMENT_UPDATE_REQUEST_TAG);
//        ApiService apiService = ApiManager.getApi().create(ApiService.class);
//        apiService.updateRequest(request.getId(),)
    }

    private void onBackPressed(){

        RecycleCardsFragment main = (RecycleCardsFragment) getActivity().getSupportFragmentManager().findFragmentByTag(Const.FRAGMENT_MAIN_TAG);
        main.removeFrag(this);
    }
}

