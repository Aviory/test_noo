package com.findchildren.avi.test.api;

import android.content.Context;

import com.findchildren.avi.test.utils.LogUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Avi on 20.09.2017.
 */

public class Queries {

    public static ApiService getUserToken(String name, String pass, Context context) {
        ApiService apiService = ApiManager.getApi().create(ApiService.class);


        return apiService;
//        apiService.getUserSession("", deviseToken)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Subscriber<Response<String>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        //getAllUserBook(deviseToken, context, Prefs.getUserSession(context, "USER_SESSION_ID"));
//                    }
//
//                    @Override
//                    public void onNext(Response<String> userSessionResponse) {
////                        if (userSessionResponse.isSuccessful()) {
////                            UserSession userSession = userSessionResponse.body();
////                            Prefs.saveUserSession(context, Const.USER_SESSION_ID, userSession.getCustomerId());
////                            LogUtil.log(this, "Save User session id");
////                            getAllUserBook(deviseToken, context, Prefs.getUserSession(context, Const.USER_SESSION_ID));
////                        }
//                    }
//                });
    }
}
