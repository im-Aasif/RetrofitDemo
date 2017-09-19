package com.io.retrofitdemo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.io.retrofitdemo.MainActivity;
import com.io.retrofitdemo.R;
import com.io.retrofitdemo.models.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GLaDOS on 9/19/2017.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private List<Post> mPosts;
    private Context mContext;
    private PostItemListener mItemListener;

    public PostsAdapter(MainActivity mContext, ArrayList<Post> mPosts, PostItemListener mItemListener) {
        this.mPosts = mPosts;
        this.mContext = mContext;
        this.mItemListener = mItemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.post_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView, this.mItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Post post = mPosts.get(position);
        holder.tvTitle.setText(post.getTitle());
        holder.tvBody.setText(post.getBody());
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvTitle, tvBody;
        PostItemListener postItemListener;

        public ViewHolder(View itemView, PostItemListener postItemListener) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvBody = (TextView) itemView.findViewById(R.id.tv_body);
            this.postItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Post post = getPost(getAdapterPosition());
            this.postItemListener.onPostClick(post.getId());

            notifyDataSetChanged();
        }
    }

    public void updatePosts(List<Post> posts) {
        mPosts = posts;
        notifyDataSetChanged();
    }

    private Post getPost(int position) {
        return mPosts.get(position);
    }

    public interface PostItemListener {
        void onPostClick(long id);
    }
}
