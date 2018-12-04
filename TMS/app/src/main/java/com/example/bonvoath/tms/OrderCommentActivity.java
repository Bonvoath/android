package com.example.bonvoath.tms;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bonvoath.tms.Entities.Comment;
import com.example.bonvoath.tms.utils.CommentListAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderCommentActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtComment;
    List<Comment> commentList = new ArrayList<>();
    CommentListAdapter mCommentListAdapter;
    SharedPreferences mSharedPreferences;
    String truckNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_comment);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(R.string.comments);
        }
        mSharedPreferences = getApplicationContext().getSharedPreferences("Auth", MODE_PRIVATE);
        truckNumber = mSharedPreferences.getString("TruckNumber", "");
        RecyclerView listView = findViewById(R.id.list_comment);
        mCommentListAdapter= new CommentListAdapter(commentList);
        listView.setNestedScrollingEnabled(false);
        listView.setAdapter(mCommentListAdapter);
        listView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        txtComment = findViewById(R.id.comment);
        Button btnSend = findViewById(R.id.ivPostComment);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment comment = new Comment();
                comment.setComment(txtComment.getText().toString());
                comment.setUser_id(truckNumber);
                commentList.add(comment);
                mCommentListAdapter.notifyDataSetChanged();
                txtComment.setText("");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(getWindow() != null) {
            this.overridePendingTransition(R.anim.slide_in_right_to_left,
                    R.anim.slide_out_right_to_left);
        }
    }
}
