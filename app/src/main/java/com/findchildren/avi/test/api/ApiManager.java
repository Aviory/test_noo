package com.findchildren.avi.test.api;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.Toast;

import com.findchildren.avi.test.Const;
import com.findchildren.avi.test.models.Request;
import com.findchildren.avi.test.prefs.Prefs;
import com.findchildren.avi.test.ui.MainActivity;
import com.findchildren.avi.test.utils.LogUtil;
import com.findchildren.avi.test.utils.UiUtil;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Avi on 20.09.2017.
 */

public class ApiManager {
    public static Retrofit sRetrofitApiAry = null;
    private static AuthenticationInterceptor mInterceptor;


    public static Retrofit getApi() {
        if (sRetrofitApiAry == null) {
            sRetrofitApiAry = new Retrofit.Builder().
                    baseUrl(Const.BASE_URL_API).
                 //   addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                    addConverterFactory(GsonConverterFactory.create()).
                    client(createHttpClient().build()).
                    build();
        }
        return sRetrofitApiAry;
    }

    private static OkHttpClient.Builder createHttpClient() {
        // init cookie manager
        CookieHandler cookieHandler = new CookieManager();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        //set your desired log level
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor)
                .cookieJar(new JavaNetCookieJar(cookieHandler))
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .followRedirects(false)
                .readTimeout(30, TimeUnit.SECONDS);
        client.interceptors().add(mInterceptor);
        return client;
    }

    public static void auth (Activity context, String name, String pass) {
        String authToken = Credentials.basic(name, pass);
        Prefs.putToken(context, authToken);

        auth(context, authToken);
    }

    public static void auth (final Activity context, final String token){
        final Toast toast = Toast.makeText(context, "Check login or pass", Toast.LENGTH_SHORT);
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        mInterceptor = new AuthenticationInterceptor(token, new AuthenticationInterceptor.Call() {
            @Override
            public void callback(Response response) {
                if(response.code()>=400 && response.code()<=412){
                    progressDialog.dismiss();
                    toast.show();
                }
                if(response.code()>=200 && response.code()<=208) {
                    LogUtil.log("auth ", "check");
                    progressDialog.dismiss();
                    String out = Prefs.getString(context,"checkauth",null);
                    if(out!=null)
                    if(out.equals("0")){
                        Prefs.putString(context,"checkauth", "1");
                        mInterceptor = new AuthenticationInterceptor(token);
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                    }
                }
            }
        });

        ApiService apiService = ApiManager.getApi().create(ApiService.class);
        apiService.getToken(0,1).enqueue(new Callback<List<Request>>() {
            @Override
            public void onResponse(Call<List<Request>> call, retrofit2.Response<List<Request>> response) {

            }

            @Override
            public void onFailure(Call<List<Request>> call, Throwable t) {

            }
        });
    }
}
