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
import com.findchildren.avi.test.models.Request;

import butterknife.BindView;
import butterknife.ButterKnife;

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
            case R.id.btn_get_comments:
                RecycleCardsFragment main = (RecycleCardsFragment) getActivity().getSupportFragmentManager().findFragmentByTag(Const.FRAGMENT_MAIN_TAG);
                main.getFragmentComment(request.getId());
                break;
        }
    }
    private void onBackPressed(){
        RecycleCardsFragment main = (RecycleCardsFragment) getActivity().getSupportFragmentManager().findFragmentByTag(Const.FRAGMENT_MAIN_TAG);
        main.removeFrag(this);

//        FragmentManager fm = getActivity().getSupportFragmentManager();
//        if (fm.getBackStackEntryCount() > 0)
//            fm.popBackStack();

    }
}

