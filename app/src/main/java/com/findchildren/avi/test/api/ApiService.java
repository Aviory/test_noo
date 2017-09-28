package com.findchildren.avi.test.api;

import com.findchildren.avi.test.models.Comment;
import com.findchildren.avi.test.models.Request;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Avi on 20.09.2017.
 */

public interface ApiService {

    @POST(" ")
    Call<String> getToken();

    @GET("search")
    Call<List<Request>> getAll(@Query("offset") Integer offset, @Query("limit") Integer limit);

    @GET("search/{id}/comments")
    Call<List<Comment>> getComments(@Part("id") long id,@Query("offset") Integer offset, @Query("limit") Integer limit);
}
