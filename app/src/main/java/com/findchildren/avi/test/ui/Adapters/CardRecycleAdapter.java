package com.findchildren.avi.test.ui.Adapters;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.findchildren.avi.test.Colors;
import com.findchildren.avi.test.R;
import com.findchildren.avi.test.models.Request;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Avi on 26.09.2017.
 */

public class CardRecycleAdapter extends RecyclerView.Adapter<CardRecycleAdapter.ViewHolder> {

    private List<Request> mList;
    private OnCardClicked mOnClick;

    public interface OnCardClicked{
        void onClickCard(int position);
        void onScrollChange(int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mCardTxtName.setText(mList.get(position).getName());
        holder.mCardTxtAge.setText(mList.get(position).getDateOfBirth());
        holder.mCardTxtSex.setText(mList.get(position).getGender());
        holder.mCardTxtArea.setText(mList.get(position).getRegion());
        holder.mContainerCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClick.onClickCard(position);
            }
        });
        if(mList.get(position).getStatus().equals("SEARCHING")){
            holder.mContainerCardView.setBackgroundColor(Colors.RED);
        }else
            holder.mContainerCardView.setBackgroundColor(Colors.GREEN);

    }
    public void setCardOnClickListener(OnCardClicked mOnClick){
        this.mOnClick = mOnClick;
    }
    public void setListRequest(List<Request> list){
        mList = list;
    }


    @Override
    public int getItemCount() {
        if(mList==null){
            return 0;
        }
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cv)
        protected CardView mContainerCardView;
        @BindView(R.id.item_card_txt_name)
        protected TextView mCardTxtName;
        @BindView(R.id.item_card_txt_age)
        protected TextView mCardTxtAge;
        @BindView(R.id.item_card_txt_sex)
        protected TextView mCardTxtSex;
        @BindView(R.id.item_card_txt_area)
        protected TextView mCardTxtArea;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
