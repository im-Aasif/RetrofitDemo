package com.io.retrofitdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.io.retrofitdemo.adapters.CommentsAdapter;
import com.io.retrofitdemo.models.Comment;
import com.io.retrofitdemo.rest.ApiUtils;
import com.io.retrofitdemo.rest.RestService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity {

    private String postId;
    private CommentsAdapter commentsAdapter;
    private RestService restService;
    private RecyclerView recyclerView;
    private String TAG = CommentActivity.class.getSimpleName();
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        postId = String.valueOf(getIntent().getExtras().getLong("postId"));
        prepare();
    }

    private void prepare() {
        restService = ApiUtils.getRestService();
        
        recyclerView = (RecyclerView) findViewById(R.id.comments_view);
        commentsAdapter = new CommentsAdapter(this, new ArrayList<Comment>(0), new CommentsAdapter.CommentItemListener() {
            @Override
            public void onCommentClick(long id) {
                Toast.makeText(CommentActivity.this, "Comment id is " + id, Toast.LENGTH_SHORT).show();   
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(commentsAdapter);
        recyclerView.setHasFixedSize(true);
        
        loadComments();
    }

    private void loadComments() {
        restService.getComments(Integer.valueOf(postId)).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    commentsAdapter.updatePosts(response.body());
                    Log.d(TAG, "onResponse: comments loaded from API");
                } else {
                    int statusCode = response.code();
                    Log.d(TAG, "onResponse: StatusCode: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.d(TAG, "onFailure: error loading from API");
            }
        });
    }

}
