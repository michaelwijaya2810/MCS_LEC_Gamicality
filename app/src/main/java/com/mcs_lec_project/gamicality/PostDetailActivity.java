package com.mcs_lec_project.gamicality;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PostDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = PostDetailActivity.class.getSimpleName();
    private Boolean isLiked;
    private int likeCount;

    private TextView tvUsernameDate;
    private TextView tvGameTitle;
    private TextView tvPostTitle;
    private TextView tvPostBody;
    private ImageButton btnLike;
    private TextView tvLikeCount;
    private ImageButton btnReply;
    private TextView tvReplyCount;
    private RecyclerView rvReplyList;
    private FloatingActionButton fabReply;

    public PostDetailActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        setupActionBar();
        setupViews();

//        get Post object from database
//        Post post = ;
//        Post post = new Post();
//        get post's author user from Post's UserID FK??
//        User author = ;
//        User author = new User();

//        concat username with post date
//        tvUsernameDate.setText(author.getUsername() + " - " + post.getPostDate());

//        get game title from post's game ID??
//        tvGameTitle.setText();

//        get post title from post
//        tvPostTitle.setText(post.getTitle());

//        get post body from post
//        tvPostBody.setText(post.getBody());

//        get like count from post??
//        likeCount = post.getLikeCount();
//        tvLikeCount.setText(likeCount);

//        get reply count from post's reply arraylist.size
//        tvReplyCount.setText(post.getReplyList().size());

//        get model data for reply list, from post's reply array list?
//        ArrayList<ReplyPost> replyList = post.getReplyList();
        //setup recyclerView
//        ReplyAdapter adapter = new ReplyAdapter(replyList);
//        rvReplyList.setAdapter(adapter);
        rvReplyList.setLayoutManager(new LinearLayoutManager(this));

//        initialize isLiked value from DB, by checking if user liked the post before or not?
//        isLiked = ;
//        if(isLiked){
//            btnLike.setImageResource(R.drawable.favorite_filled_icon);
//            Log.d(TAG, "isLiked true");
//        }else{
//            btnLike.setImageResource(R.drawable.favorite_icon);
//            Log.d(TAG, "isLiked false");
//        }

        //buttons' setOnClickListener
        btnLike.setOnClickListener(this);
        btnReply.setOnClickListener(this);
        fabReply.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.post_menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.post_menu_share){
            Toast.makeText(this, "Post shared!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Post Shared!");
            return true;
        }else if(item.getItemId() == R.id.post_menu_bookmark){
//            Add post to user's bookmark database

            Toast.makeText(this, "Post bookmarked!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Post bookmarked!");
            return true;
        }else if(item.getItemId() == R.id.post_menu_report){
            //Show dialog to report
            MaterialAlertDialogBuilder dialogBuilder =
                    new MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_App_MaterialAlertDialog)
                    .setTitle("Report")
                    .setMessage("Report this post?")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //close dialog too
                            Log.d(TAG, "Negative return: " + which);
                        }
                    })
                    .setPositiveButton("Report", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //close dialog
                            Log.d(TAG, "Positive return: " + which);
                            Toast.makeText(PostDetailActivity.this,
                                    "Post reported!", Toast.LENGTH_SHORT).show();
                        }
                    });
            dialogBuilder.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btnReply.getId() || v.getId() == fabReply.getId()){
            startActivity(new Intent(this, ReplyActivity.class));
        }else if(v.getId() == btnLike.getId()){
//            if(!isLiked){ //post not liked yet
//                btnLike.setImageResource(R.drawable.favorite_filled_icon);
//                //increment like count
//                likeCount++;
//            }else{ //post liked before
//                btnLike.setImageResource(R.drawable.favorite_icon);
//                //decrement like count
//                likeCount--;
//            }
////            display new value, update post like count in DB with likeCount
//            tvLikeCount.setText(likeCount);
            Toast.makeText(this, "You tried to push the button!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.back_icon);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        Log.i(TAG, "ActionBar ready!");
    }

    private void setupViews(){
        tvUsernameDate = findViewById(R.id.tv_username_date);
        tvGameTitle = findViewById(R.id.tv_game_title);
        tvPostTitle = findViewById(R.id.tv_post_title);
        tvPostBody = findViewById(R.id.tv_post_body);
        btnLike = findViewById(R.id.btn_like);
        tvLikeCount = findViewById(R.id.tv_like_count);
        btnReply = findViewById(R.id.btn_reply);
        tvReplyCount = findViewById(R.id.tv_reply_count);
        rvReplyList = findViewById(R.id.rv_reply_list);
        fabReply = findViewById(R.id.fab_reply);
        Log.i(TAG, "Views ready!");
    }
}