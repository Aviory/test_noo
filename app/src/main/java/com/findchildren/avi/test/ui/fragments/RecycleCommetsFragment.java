package com.findchildren.avi.test.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.findchildren.avi.test.R;
import com.findchildren.avi.test.api.ApiManager;
import com.findchildren.avi.test.api.ApiService;
import com.findchildren.avi.test.models.Comment;
import com.findchildren.avi.test.models.Request;
import com.findchildren.avi.test.ui.Adapters.CardRecycleAdapter;
import com.findchildren.avi.test.ui.Adapters.CommentRecycleAdapter;
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

public class RecycleCommetsFragment extends Fragment {

    @BindView(R.id.comment_recycle)
    protected RecyclerView recyclerView;

    private List<Comment> mCommentList;
    private CommentRecycleAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycle_comments, container, false);
        ButterKnife.bind(this, rootView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CommentRecycleAdapter();
        setmCommentList();
//        mAdapter.setCardOnClickListener(this);

        return rootView;
    }

    public void setmCommentList() {
        ApiService apiService = ApiManager.getApi().create(ApiService.class);
        apiService.getComments(19, 0,10).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                LogUtil.log("TAG", "onResponse");
                List<Comment> listComment = response.body();
                mAdapter.setList(listComment);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                LogUtil.log("TAG", "onFailure");
            }
        });

    }
}
