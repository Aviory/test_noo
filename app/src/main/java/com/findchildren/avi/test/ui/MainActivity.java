package com.findchildren.avi.test.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.findchildren.avi.test.Const;
import com.findchildren.avi.test.R;
import com.findchildren.avi.test.ui.fragments.RecycleCardsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_container)
    protected RelativeLayout container;

    FragmentTransaction fragmentTransaction;
    RecycleCardsFragment cardsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        cardsFragment = new RecycleCardsFragment();
        addFrag(cardsFragment, Const.FRAGMENT_MAIN_TAG);
    }

    private void addFrag(Fragment fragment, String tag) {
        fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.add(R.id.main_container, fragment, tag);
        fragmentTransaction.commit();
        getSupportFragmentManager().beginTransaction().show(fragment).commit();

//        UiUtil.hideView(mLeftMenuLayout);
    }
    private void removeFrag(Fragment fragment, String tag) {
        fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
        getSupportFragmentManager().beginTransaction().show(fragment).commit();

//        UiUtil.hideView(mLeftMenuLayout);
    }
}