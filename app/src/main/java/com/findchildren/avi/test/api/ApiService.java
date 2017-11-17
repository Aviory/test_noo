package com.findchildren.avi.test.api;

import com.findchildren.avi.test.models.Comment;
import com.findchildren.avi.test.models.Request;
import com.findchildren.avi.test.models.RequestComment;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Avi on 20.09.2017.
 */

public interface ApiService {

//
//    @GET("search")
//    Observable<Response<List<Request>>> getToken(@Query("offset") Integer offset, @Query("limit") Integer limit);

    @GET("search")
    Call<List<Request>> getToken(@Query("offset") Integer offset, @Query("limit") Integer limit);

    //Requests -----------

    @GET("search")
    Observable<Response<List<Request>>> getAll(@Query("offset") Integer offset, @Query("limit") Integer limit);

    @POST("search")
    Observable<Response<Request>> sendNewRequest(@Body Request request);

    @PUT("search/{id}")
    Observable<Response<Request>> updateRequest(@Path("id") long id, @Body Request request);

    @DELETE("search/{id}")
    Observable<Response<ResponseBody>> deleteRequest(@Path("id") long id);

    //Comments -------------

    @GET("search/{id}/comments")
    Observable<Response<List<Comment>>> getComments(@Path("id") long id, @Query("offset") Integer offset, @Query("limit") Integer limit);

    @Headers("Content-Type:text/plain; charset=utf-8")
    @POST("search/{id}/comments")
    Observable<Response<RequestComment>> sendMsg(@Path("id") long id, @Body String comments);

    @Headers("Content-Type:text/plain; charset=utf-8")
    @PUT("search/{id}/comments/{comId}")
    Observable<Response<RequestComment>> updateComment(@Path("id") long id,@Path("comId") long comId, @Body String msg);

    @DELETE("search/{id}/comments/{comId}")
    Observable<Response<ResponseBody>> removeComment(@Path("id") long id,@Path("comId") long comId );

}
