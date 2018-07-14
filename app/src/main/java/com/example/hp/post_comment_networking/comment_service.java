package com.example.hp.post_comment_networking;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface comment_service {
    @GET("comments")
    Call<ArrayList<comments>>getcomments();
}
