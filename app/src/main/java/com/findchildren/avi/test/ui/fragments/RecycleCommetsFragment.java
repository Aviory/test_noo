package com.findchildren.avi.test.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.findchildren.avi.test.ui.Adapters.CommentRecycleAdapter;
import com.findchildren.avi.test.ui.alerts.AlertAddComent;
import com.findchildren.avi.test.ui.alerts.AlertChangeComment;
import com.findchildren.avi.test.utils.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Avi on 28.09.2017.
 */

public class RecycleCommetsFragment extends Fragment implements View.OnClickListener, CommentRecycleAdapter.OnCommentClicked {

    @BindView(R.id.comment_recycle)
    protected RecyclerView recyclerView;
    @BindView(R.id.btn_add_comment)
    protected Button btnAddComment;
    @BindView(R.id.btn_comments_close)
    protected Button btnCommentClose;

    private List<Comment> mCommentList;
    private CommentRecycleAdapter mAdapter;
    private int currentComment;
    AlertChangeComment alertChangeComment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycle_comments, container, false);
        ButterKnife.bind(this, rootView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CommentRecycleAdapter();
        btnAddComment.setOnClickListener(this);
        btnCommentClose.setOnClickListener(this);
        getCommentList();
        return rootView;
    }

    public void getCommentList() {
        Long id = getArguments().getLong(Const.CURRENT_USER_ID);
        ApiService apiService = ApiManager.getApi().create(ApiService.class);
        apiService.getComments(id, 0,10).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                LogUtil.log("TAG", "onResponse");
                List<Comment> listComment = response.body();
                mAdapter.setList(listComment);
                recyclerView.setAdapter(mAdapter);
                mCommentList = listComment;
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                LogUtil.log("TAG", "onFailure");
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_comment:
                AlertAddComent.getInstance().setArguments(getArguments());
                AlertAddComent.getInstance().show(getChildFragmentManager(), Const.DIALOG_ADD_COMENT);
                break;
            case R.id.btn_comments_close:
                RecycleCardsFragment main = (RecycleCardsFragment) getActivity().getSupportFragmentManager().findFragmentByTag(Const.FRAGMENT_MAIN_TAG);
                main.removeFrag(this);
                break;
            case R.id.btn_delete:
                removeComent();
                alertChangeComment.dismiss();
                break;
            case R.id.btn_update:
                updateComment();
                alertChangeComment.dismiss();
                break;
        }
    }

    private void updateComment() {

    }

    private void removeComent() {

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
        ApiService apiService = ApiManager.getApi().create(ApiService.class);
//        apiService.
    }

    @Override
    public void onLongClick(int position) {

    }
}
