package com.example.practicaltestfazal.api;

import com.example.practicaltestfazal.model.PostModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("posts")
    Call<ArrayList<PostModel>> getPosts(@Query("page") int page, @Query("limit") int limit);
}
