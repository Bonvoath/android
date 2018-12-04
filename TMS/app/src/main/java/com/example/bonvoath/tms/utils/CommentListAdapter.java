package com.example.bonvoath.tms.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bonvoath.tms.Entities.Comment;
import com.example.bonvoath.tms.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.Holder> {
    private List<Comment> data;
    public CommentListAdapter(List<Comment> comments){
        data = comments;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_comment, parent, false);

        return new CommentListAdapter.Holder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentListAdapter.Holder holder, int position) {
        Comment item = data.get(position);
        holder.comment.setText(item.getComment());
        holder.username.setText(item.getUser_id());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Comment> comments){
        data = comments;
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView comment, username, timestamp, reply, likes;
        CircleImageView profileImage;
        ImageView likeIcon;
        Holder(@NonNull View itemView) {
            super(itemView);
            comment = itemView.findViewById(R.id.comment);
            username = itemView.findViewById(R.id.comment_username);
            timestamp = itemView.findViewById(R.id.comment_time_posted);
            likes = itemView.findViewById(R.id.comment_like);
            reply = itemView.findViewById(R.id.comment_reply);
            likeIcon = itemView.findViewById(R.id.comment_img_like);
            profileImage = itemView.findViewById(R.id.profile_image);
        }
    }
}
