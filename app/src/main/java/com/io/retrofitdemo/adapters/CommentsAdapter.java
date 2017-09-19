package com.io.retrofitdemo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.io.retrofitdemo.CommentActivity;
import com.io.retrofitdemo.R;
import com.io.retrofitdemo.models.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GLaDOS on 9/19/2017.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private List<Comment> mComments;
    private Context mContext;
    private CommentItemListener mItemListener;

    public CommentsAdapter(CommentActivity mContext, ArrayList<Comment> mPosts, CommentItemListener mItemListener) {
        this.mComments = mPosts;
        this.mContext = mContext;
        this.mItemListener = mItemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.comment_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView, this.mItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Comment comment = mComments.get(position);
        holder.tvName.setText(comment.getName());
        holder.tvEmail.setText(comment.getEmail());
        holder.tvBody.setText(comment.getBody());
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvName, tvEmail, tvBody;
        CommentItemListener postItemListener;

        public ViewHolder(View itemView, CommentItemListener postItemListener) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvEmail = (TextView) itemView.findViewById(R.id.tv_email);
            tvBody = (TextView) itemView.findViewById(R.id.tv_cmntbody);
            this.postItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Comment post = getPost(getAdapterPosition());
            this.postItemListener.onCommentClick(post.getId());

            notifyDataSetChanged();
        }
    }

    public void updatePosts(List<Comment> posts) {
        mComments = posts;
        notifyDataSetChanged();
    }

    private Comment getPost(int position) {
        return mComments.get(position);
    }

    public interface CommentItemListener {
        void onCommentClick(long id);
    }
}
