package com.findchildren.avi.test.ui.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.findchildren.avi.test.R;
import com.findchildren.avi.test.models.Comment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Avi on 28.09.2017.
 */

public class CommentRecycleAdapter extends RecyclerView.Adapter<CommentRecycleAdapter.ViewHolder> {
    private List<Comment> mList;
    private OnCommentClicked listener;

    public void setListener(OnCommentClicked listener) {
        this.listener = listener;
    }

    public interface OnCommentClicked{
        void onClickComment(int position);
        void onScrollChange(int position);
        void onLongClick(int position);
    }

    @Override
    public CommentRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mCommentAuthor.setText(mList.get(position).getSenderName());
        holder.mCommentBody.setText(mList.get(position).getText());
        holder.mCommentDate.setText(mList.get(position).getTimeOfCreate());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickComment(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(List<Comment> list){
        mList = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.comment_cardview)
        protected CardView mCardView;
        @BindView(R.id.comment_author)
        protected TextView mCommentAuthor;
        @BindView(R.id.comment_body)
        protected TextView mCommentBody;
        @BindView(R.id.comment_date)
        protected TextView mCommentDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}