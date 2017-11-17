package com.findchildren.avi.test.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.findchildren.avi.test.Const;
import com.findchildren.avi.test.R;
import com.findchildren.avi.test.api.ApiManager;
import com.findchildren.avi.test.api.ApiService;
import com.findchildren.avi.test.models.Comment;
import com.findchildren.avi.test.models.RequestComment;
import com.findchildren.avi.test.ui.Adapters.CommentRecycleAdapter;
import com.findchildren.avi.test.ui.alerts.AlertAddComent;
import com.findchildren.avi.test.ui.alerts.AlertChangeComment;
import com.findchildren.avi.test.utils.LogUtil;
import com.findchildren.avi.test.utils.UiUtil;

import java.util.LinkedList;
import java.util.List;

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
 * Created by Avi on 28.09.2017.
 */

public class RecycleCommetsFragment extends Fragment implements View.OnClickListener, CommentRecycleAdapter.OnCommentClicked, AlertAddComent.CallBack, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.comment_recycle)
    protected RecyclerView recyclerView;
    @BindView(R.id.btn_add_comment)
    protected Button btnAddComment;
    @BindView(R.id.btn_comments_close)
    protected Button btnCommentClose;
    @BindView(R.id.refreshView)
    protected SwipeRefreshLayout refreshLayout;


    private List<Comment> mCommentList;
    private CommentRecycleAdapter mAdapter;
    private int currentComment;
    private long currentUser;
    AlertChangeComment alertChangeComment;
    AlertAddComent alertAddComent;
    String type;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycle_comments, container, false);
        ButterKnife.bind(this, rootView);

        refreshLayout.setOnRefreshListener(this);
        mCommentList = new LinkedList<Comment>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CommentRecycleAdapter();
        mAdapter.setListener(this);
        recyclerView.setAdapter(mAdapter);
        btnAddComment.setOnClickListener(this);
        btnCommentClose.setOnClickListener(this);
        getCommentList(mCommentList.size());
        return rootView;
    }

    private void getCommentList(final int startPosition) {
        currentUser = getArguments().getLong(Const.CURRENT_USER_ID);
        ApiService apiService = ApiManager.getApi().create(ApiService.class);
        apiService.getComments(currentUser, startPosition,Const.LIMIT_LOAD_REQUEST)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<Response<List<Comment>>>() {
            @Override
            public void call(Response<List<Comment>> response) {
                if(response.code()>=200 && response.code()<=202) {
                    refreshLayout.setRefreshing(false);
                    if(response.body().size()!=0){
                        if(startPosition==0){
                            if( mCommentList.size()!=0){
                                for (int i=0;i<response.body().size();i++){
                                    if(mCommentList.get(i).getId()==(response.body().get(i).getId())){
                                        mCommentList.set(i,response.body().get(i));
                                    }else {
                                        if(i!=0){
                                            mAdapter.notifyDataSetChanged();
                                        }
                                        return;
                                    }
                                }
                            }else{
                                mCommentList.addAll(response.body());
                                mAdapter.notifyDataSetChanged();
                            }

                        }else{
                            mCommentList.addAll(response.body());
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        })
        .subscribe();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_comment:
                alertAddComent = new AlertAddComent();
                alertAddComent.setCallBack(this);
                type=Const.ALERT_TYPE_ADD_MSG;
                alertAddComent.show(getChildFragmentManager(), Const.DIALOG_ADD_COMENT);
                break;
            case R.id.btn_comments_close:
                RecycleCardsFragment main = (RecycleCardsFragment) getActivity().getSupportFragmentManager().findFragmentByTag(Const.FRAGMENT_MAIN_TAG);
                main.removeFrag(this);
                break;
            case R.id.btn_remove:
                removeComent();
                break;
            case R.id.btn_update:
                alertChangeComment.dismiss();
                type=Const.ALERT_TYPE_UPDATE_MSG;

                alertAddComent = new AlertAddComent();
                getArguments().putLong(Const.CURRENT_MSG_ID, currentComment);
                alertAddComent.setCallBack(this);
                alertAddComent.show(getChildFragmentManager(), Const.DIALOG_ADD_COMENT);
                break;
        }
    }

    private void sendMsg(String msg){
        ApiService apiService = ApiManager.getApi().create(ApiService.class);

        LogUtil.log("TAG", "id: "+currentUser+" txt"+msg);
        apiService.sendMsg(currentUser,msg)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<Response<RequestComment>>() {
                    @Override
                    public void call(Response<RequestComment> response) {
                        LogUtil.log("TAG", "onFailure: "+response.message());
                        LogUtil.log("TAG", "onFailure: "+response.body());
                        if(response.code()>=200 && response.code()<=202){
                            UiUtil.showToast(getActivity(), "request successful remove");
                            alertAddComent.dismiss();
                            RequestComment newMsg= response.body();
                            mCommentList.add(newMsg);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                })
                .subscribe();
    }

    private void updateComment(String msg) {
        ApiService apiService = ApiManager.getApi().create(ApiService.class);
        apiService.updateComment(currentUser,mCommentList.get(currentComment).getId(),msg)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<Response<RequestComment>>() {
                    @Override
                    public void call(Response<RequestComment> response) {
                        LogUtil.log("TAG", "onResponse updateComment: "+response.message());
                        LogUtil.log("TAG", "onResponse updateComment: "+response.body());
                        if(response.code()>=200 && response.code()<=202){
                            UiUtil.showToast(getActivity(), "request successful update");
                            alertAddComent.dismiss();
                            RequestComment newMsg= response.body();
                            mCommentList.set(currentComment, newMsg);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                })
                .subscribe();
    }

    private void removeComent() {
        ApiService apiService = ApiManager.getApi().create(ApiService.class);
        apiService.removeComment(currentUser,mCommentList.get(currentComment).getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<Response<ResponseBody>>() {
                    @Override
                    public void call(Response<ResponseBody> response) {
                        LogUtil.log("TAG", "onResponse: "+response.message());
                        LogUtil.log("TAG", "onResponse: "+response.body());
                        if(response.code()>=200 && response.code()<=202){
                            alertChangeComment.dismiss();
//                    getCommentList();
                            mCommentList.remove(currentComment);
                            mAdapter.notifyDataSetChanged();
                            UiUtil.showToast(getActivity(), "comment successful remove");
                    }
                }
            });
    }

    @Override
    public void onClickComment(int position) {
        alertChangeComment = new AlertChangeComment();
        alertChangeComment.setOnClick(this);
        alertChangeComment.show(getFragmentManager(),Const.CHANGE_COMMENT);
        currentComment = position;
    }

    @Override
    public void onScrollChange(int position) {
        getCommentList(mCommentList.size());
    }

    @Override
    public void alertOnClick(String msg) {
        LogUtil.log("TAG", "type: "+type);
        if(type.equals(Const.ALERT_TYPE_ADD_MSG)){
            sendMsg(msg);
        }
        if(type.equals(Const.ALERT_TYPE_UPDATE_MSG)){
            updateComment(msg);
        }
    }

    @Override
    public void onRefresh() {
        getCommentList(0);
    }
}
