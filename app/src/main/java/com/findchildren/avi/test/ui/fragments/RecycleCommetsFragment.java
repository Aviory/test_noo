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
import com.findchildren.avi.test.models.Comment;
import com.findchildren.avi.test.models.Request;
import com.findchildren.avi.test.ui.Adapters.CardRecycleAdapter;
import com.findchildren.avi.test.ui.Adapters.CommentRecycleAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

//        mAdapter.setCardOnClickListener(this);
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }

    public void setmCommentList(List<Comment> mCommentList) {
        this.mCommentList = mCommentList;
        mAdapter.setList(mCommentList);
    }
}
