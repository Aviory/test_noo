package com.findchildren.avi.test.api;

import com.findchildren.avi.test.models.Comment;
import com.findchildren.avi.test.models.Request;
import com.findchildren.avi.test.models.RequestComment;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Avi on 20.09.2017.
 */

public interface ApiService {

    @GET("search")
    Call<List<Request>> getToken(@Query("offset") Integer offset, @Query("limit") Integer limit);

    //Requests -----------

    @GET("search")
    Call<List<Request>> getAll(@Query("offset") Integer offset, @Query("limit") Integer limit);

    @POST("search")
    Call<Request> sendNewRequest(@Body Request request);

    @PUT("search/{id}")
    Call<Request> updateRequest(@Path("id") long id, @Body Request request);

    @DELETE("search/{id}")
    Call<ResponseBody> deleteRequest(@Path("id") long id);

    //Comments -------------

    @GET("search/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") long id, @Query("offset") Integer offset, @Query("limit") Integer limit);

    @Headers("Content-Type:text/plain; charset=utf-8")
    @POST("search/{id}/comments")
    Call<RequestComment> sendMsg(@Path("id") long id, @Body String comments);

    @PUT("children/{id}/comments/{comId}")
    Call<String> updateComment(@Path("id") long id,@Path("comId") long comId, @Body Comment request);

    @DELETE("children/{id}/comments/{comId}")
    Call<String> deleteComment(@Path("id") long id,@Path("comId") long comId );

}
