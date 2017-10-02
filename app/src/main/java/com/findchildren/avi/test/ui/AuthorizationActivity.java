package com.findchildren.avi.test.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.findchildren.avi.test.R;
import com.findchildren.avi.test.api.ApiManager;
import com.findchildren.avi.test.prefs.Prefs;
import com.findchildren.avi.test.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Avi on 20.09.2017.
 */

public class AuthorizationActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.txt_login)
    protected EditText txtLogin;
    @BindView(R.id.txt_pass)
    protected EditText txtPass;
    @BindView(R.id.auth_btn)
    protected Button btnAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        ButterKnife.bind(this);

        Prefs.putString(this,"checkauth", "0");
        checkToken();
        btnAuth.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //todo reg ex
        String log = "andrey";
        String pas = "and123and";
//        auth(txtLogin.getText().toString(),txtPass.getText().toString());
        auth(log,pas);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkToken();
    }

    private void checkToken(){
        String token = Prefs.getToken(this);
        if(token!=null) {
            LogUtil.log("auth token  ", token);
            if (!token.equals("")) {
                ApiManager.auth(this, token);
            }
        }
    }

    private void auth(final String login, final String pass) {
        ApiManager.auth(this,login,pass);
    }
}
