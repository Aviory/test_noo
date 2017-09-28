package com.findchildren.avi.test.api;

import com.findchildren.avi.test.models.Comment;
import com.findchildren.avi.test.models.Request;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Avi on 20.09.2017.
 */

public interface ApiService {


    @POST(" ")
    Call<String> getToken();

//    @GET("/{userName}/{userPass}")
//    Call<String> getToken(@Part("userName") String name, @Part("userPass") String pass);

    @GET("search?offset=0&limit=10")
    Call<List<Request>> getAll();

    @GET("search/${id}/comments?offset=0&limit=10")
    Call<List<Comment>> getComments(@Part("userName") long id);
}
