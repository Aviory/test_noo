package com.findchildren.avi.test.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.findchildren.avi.test.Const;
import com.findchildren.avi.test.R;
import com.findchildren.avi.test.api.ApiManager;
import com.findchildren.avi.test.prefs.Prefs;
import com.findchildren.avi.test.ui.alerts.AlertNewRequest;
import com.findchildren.avi.test.ui.fragments.RecycleCardsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.main_container)
    protected RelativeLayout container;
    @BindView(R.id.logout)
    protected ImageView btnLogout;
    @BindView(R.id.new_request)
    protected ImageView btnNewRequest;

    FragmentTransaction fragmentTransaction;
    RecycleCardsFragment cardsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mActionBarToolbar);

        cardsFragment = RecycleCardsFragment.getInstance();
        addFrag(cardsFragment, Const.FRAGMENT_MAIN_TAG);
        btnLogout.setOnClickListener(this);
        btnNewRequest.setOnClickListener(this);
    }

    public void addFrag(Fragment fragment, String tag) {
        fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment, tag);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logout:
                //todo token del
                Prefs.clearPrefs(this);
                ApiManager.auth(this,"");
                Intent intent = new Intent(MainActivity.this, AuthorizationActivity.class);
                startActivity(intent);
                break;
            case R.id.new_request:
                AlertNewRequest alertNewRequest = new AlertNewRequest();
                addFrag(alertNewRequest,Const.FRAGMENT_NEWREQUEST_TAG);
                break;
        }
    }
}