package com.findchildren.avi.test.api;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

import com.findchildren.avi.test.Const;
import com.findchildren.avi.test.prefs.Prefs;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Avi on 20.09.2017.
 */

public class ApiManager {
    public static Retrofit sRetrofitApiAry = null;
    private static Interceptor mInterceptor;


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

        public static void auth (Context context, String name, String pass){

            final String authToken = Credentials.basic(name, pass);
            Prefs.putToken(context, authToken);

        mInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                // Customize the request

                //todo pref
                Request request = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("Authorization", authToken)
                        .method(original.method(), original.body())
                        .build();

                Response response = chain.proceed(request);

                // Customize or return the response
                return response;
            }
        };
    }
}
