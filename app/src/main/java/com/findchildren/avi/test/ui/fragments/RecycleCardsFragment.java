package com.findchildren.avi.test.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.findchildren.avi.test.Const;
import com.findchildren.avi.test.R;
import com.findchildren.avi.test.api.ApiManager;
import com.findchildren.avi.test.api.ApiService;
import com.findchildren.avi.test.models.Request;
import com.findchildren.avi.test.ui.Adapters.CardRecycleAdapter;
import com.findchildren.avi.test.ui.MainActivity;
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

/**
 * Created by Avi on 27.09.2017.
 */

public class RecycleCardsFragment extends Fragment implements CardRecycleAdapter.OnCardClicked, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recycle)
    protected RecyclerView recyclerView;
    @BindView(R.id.refreshView)
    protected SwipeRefreshLayout refreshLayout;
    AlertChangeComment alertChangeComment;
    private int currentCard;

    public static RecycleCardsFragment getInstance(){
        RecycleCardsFragment mInstance = new RecycleCardsFragment();
        return mInstance;
    }

    private List<Request> mCardList;
    private CardRecycleAdapter mAdapter;

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.log("tag", "onStart: ");
        searchAll(mCardList.size());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycle_card, container, false);
        ButterKnife.bind(this, rootView);

        mCardList = new LinkedList<Request>();

        refreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new CardRecycleAdapter();
        mAdapter.setCardOnClickListener(this);
        mAdapter.setListRequest(mCardList);
        recyclerView.setAdapter(mAdapter);
        return rootView;
    }

    private void searchAll(final int startPosition){
        ApiService apiService = ApiManager.getApi().create(ApiService.class);
        apiService.getAll(startPosition,Const.LIMIT_LOAD_REQUEST).enqueue(new Callback<List<Request>>() {
            @Override
            public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                if(response.code()>=200 && response.code()<=202) {
                    refreshLayout.setRefreshing(false);
                    if(response.body().size()!=0){
                        if(startPosition==0){
                            if( mCardList.size()!=0){
                                for (int i=0;i<response.body().size();i++){
                                    if(!mCardList.get(i).getId().equals(response.body().get(i).getId())){
                                        mCardList.set(i,response.body().get(i));
                                    }else {
                                        if(i!=0){
                                            mAdapter.notifyDataSetChanged();
                                        }
                                        return;
                                    }
                                }
                            }else{
                                mCardList.addAll(response.body());
                                mAdapter.notifyDataSetChanged();
                            }

                        }else{
                            mCardList.addAll(response.body());
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Request>> call, Throwable t) {
                LogUtil.log("TAG", "onFailure");
            }
        });
    }
    public void getFragmentComment(long position) {
        RecycleCommetsFragment recycleCommetsFragment = new RecycleCommetsFragment();

        Bundle bundle = new Bundle();
        bundle.putLong(Const.CURRENT_USER_ID, position);
        recycleCommetsFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.card_container, recycleCommetsFragment, Const.FRAGMENT_CHAT_TAG);
        fragmentTransaction.commit();
        getChildFragmentManager().beginTransaction().show(recycleCommetsFragment).commit();

//        UiUtil.hideView(recyclerView);
    }

    private void addFrag(Fragment fragment, String tag) {

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.card_container, fragment, tag);
        fragmentTransaction.commit();
        getChildFragmentManager().beginTransaction().show(fragment).commit();

        UiUtil.hideView(recyclerView);
    }
    public void removeFrag(Fragment fragment){
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();

        UiUtil.showView(recyclerView);
    }

    @Override
    public void onClickCard(int position) {
//        UiUtil.hideView(refreshLayout);

        CardFragment cardFragment = new CardFragment();
        cardFragment.setCard(mCardList.get(position));
        addFrag(cardFragment, Const.FRAGMENT_CARD_TAG);

    }

    @Override
    public void onScrollChange(int position) {
        searchAll(mCardList.size());
    }

    @Override
    public void onRefresh() {
        searchAll(0);
    }

}
