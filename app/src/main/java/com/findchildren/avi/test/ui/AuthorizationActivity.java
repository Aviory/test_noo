package com.findchildren.avi.test.ui;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.findchildren.avi.test.Const;
import com.findchildren.avi.test.R;
import com.findchildren.avi.test.api.ApiManager;
import com.findchildren.avi.test.api.ApiService;
import com.findchildren.avi.test.api.Queries;
import com.findchildren.avi.test.api.Service;
import com.findchildren.avi.test.models.Request;
import com.findchildren.avi.test.utils.LogUtil;

import java.io.IOException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

import static rx.Notification.Kind.OnError;

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
    private ProgressBar mprogressBar;
    String basicAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        ButterKnife.bind(this);

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

    private void auth(final String login, final String pass) {
        ApiManager.auth(this,login,pass);
        ApiService apiService = Service.createService(ApiService.class, login, pass);
        Call<String> call = apiService.getToken();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                LogUtil.log("auth ", "successful");
                Intent intent = new Intent(AuthorizationActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LogUtil.log("login ", "onFailure");
            }
        });
    }
}
