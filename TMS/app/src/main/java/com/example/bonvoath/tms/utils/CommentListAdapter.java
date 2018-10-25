package com.example.bonvoath.tms.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.bonvoath.tms.Entities.Comment;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import com.example.bonvoath.tms.R;

public class CommentListAdapter extends ArrayAdapter<Comment> {

    private static final String TAG = "CommentListAdapter";
    private LayoutInflater mInflater;
    private int layoutResource;
    private Context mContext;

    public CommentListAdapter(@NonNull Context context, int resource, @NonNull List<Comment> objects) {
        super(context, resource, objects);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        layoutResource = resource;
    }

    private static class ViewHolder{
        TextView comment, username, timestamp, reply, likes;
        CircleImageView profileImage;
        ImageView likeIcon;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null)
        {
            convertView = mInflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();
            holder.comment = convertView.findViewById(R.id.comment);
            holder.username = convertView.findViewById(R.id.comment_username);
            holder.timestamp = convertView.findViewById(R.id.comment_time_posted);
            holder.likes = convertView.findViewById(R.id.comment_like);
            holder.reply = convertView.findViewById(R.id.comment_reply);
            holder.likeIcon = convertView.findViewById(R.id.comment_img_like);
            holder.profileImage = convertView.findViewById(R.id.profile_image);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        Comment item = getItem(position);
        holder.comment.setText(item.getComment());
        holder.username.setText(item.getUser_id());

        return convertView;
    }
}
