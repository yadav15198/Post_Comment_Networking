package com.example.hp.post_comment_networking;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface postService {
    @GET("posts")
    Call<ArrayList<Posts>>getPosts();
}
