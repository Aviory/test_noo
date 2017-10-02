package com.findchildren.avi.test.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Avi on 26.09.2017.
 */

public class AuthenticationInterceptor implements Interceptor {
    private Call callback;
    interface Call{
        void callback(Response response);
    }
    private Response response;
    private String authToken;

    public AuthenticationInterceptor(String token, Call callback) {
        this.authToken = token;
        this.callback = callback;
    }
    public AuthenticationInterceptor(String token) {
        this.authToken = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        // Customize the request

        Request request = original.newBuilder()
                .header("Accept", "application/json")
                .header("Authorization", authToken)
                .method(original.method(), original.body())
                .build();

        Response response = chain.proceed(request);
        // Customize or return the response
        if(callback!=null)
            callback.callback(response);
        return response;
    }
}
