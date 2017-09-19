package com.io.retrofitdemo.rest;

import com.io.retrofitdemo.models.Comment;
import com.io.retrofitdemo.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by GLaDOS on 9/19/2017.
 */

public interface RestService {

    @GET("/posts")
    Call<List<Post>> getPosts();

    @GET("/posts/{postId}/comments")
    Call<List<Comment>> getComments(@Path("postId") int postId);

}
