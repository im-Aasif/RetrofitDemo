package com.io.retrofitdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.io.retrofitdemo.adapters.PostsAdapter;
import com.io.retrofitdemo.models.Post;
import com.io.retrofitdemo.rest.ApiUtils;
import com.io.retrofitdemo.rest.RestService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private PostsAdapter postsAdapter;
    private RecyclerView recyclerView;
    private RestService restService;
    private String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepareRetro();
    }

    public void prepareRetro() {
        restService = ApiUtils.getRestService();
        recyclerView = (RecyclerView) findViewById(R.id.posts_view);
        postsAdapter = new PostsAdapter(this, new ArrayList<Post>(0), new PostsAdapter.PostItemListener() {
            @Override
            public void onPostClick(long id) {
                Toast.makeText(MainActivity.this, "Post id is "+ id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, CommentActivity.class);
                intent.putExtra("postId", id);
                startActivity(intent);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(postsAdapter);
        recyclerView.setHasFixedSize(true);

        loadPosts();
    }

    private void loadPosts() {
        restService.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    postsAdapter.updatePosts(response.body());
                    Log.d(TAG, "onResponse: posts loaded from API");
                } else {
                    int statusCode = response.code();
                    Log.d(TAG, "onResponse: StatusCode: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.d(TAG, "onFailure: error loading from API");
            }
        });
    }
}
