package com.findchildren.avi.test.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.findchildren.avi.test.Const;
import com.findchildren.avi.test.R;
import com.findchildren.avi.test.api.ApiManager;
import com.findchildren.avi.test.api.ApiService;
import com.findchildren.avi.test.models.Comment;
import com.findchildren.avi.test.models.Request;
import com.findchildren.avi.test.ui.Adapters.CardRecycleAdapter;
import com.findchildren.avi.test.utils.LogUtil;
import com.findchildren.avi.test.utils.UiUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Avi on 27.09.2017.
 */

public class RecycleCardsFragment extends Fragment implements CardRecycleAdapter.OnCardClicked{
    @BindView(R.id.recycle)
    protected RecyclerView recyclerView;

    private List<Request> mCardList;
    private CardRecycleAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycle_card, container, false);
        ButterKnife.bind(this, rootView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CardRecycleAdapter();
        mAdapter.setCardOnClickListener(this);
        searchAll();

        return rootView;
    }

    private void searchAll(){
        ApiService apiService = ApiManager.getApi().create(ApiService.class);
        apiService.getAll(0,10).enqueue(new Callback<List<Request>>() {
            @Override
            public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                mCardList = response.body();
                mAdapter.setListRequest(mCardList);
                recyclerView.setAdapter(mAdapter);
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
        LogUtil.log("tag", "onClickCard: "+position);
        if(mCardList.get(position)!=null) {
            CardFragment cardFragment = new CardFragment();
            cardFragment.setCard(mCardList.get(position));
            addFrag(cardFragment, Const.FRAGMENT_CARD_TAG);
        }else {
            LogUtil.log("tag", "onClickCard: null "+mCardList.size());

        }
    }
}
