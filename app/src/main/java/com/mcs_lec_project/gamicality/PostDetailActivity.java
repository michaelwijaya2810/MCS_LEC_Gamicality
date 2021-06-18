package com.mcs_lec_project.gamicality;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private TextView tvUsernameDate;
    private TextView tvGameTitle;
    private TextView tvPostTitle;
    private TextView tvPostBody;
    private ImageButton btnReply;
    private TextView tvReplyCount;
    private RecyclerView rvReplyList;
    private FloatingActionButton fabReply;

    private Post post;
    int currentuserid;
    int postid;
    DBHandler dbhandler;
    ArrayList<ReplyPost> replylist;
    ReplyAdapter adapter;
    Game game;
    public PostDetailActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        setupActionBar();
        setupViews();
        dbhandler = new DBHandler(this);
//        get intent from home post of the game
        Intent intent = getIntent();
        currentuserid = intent.getIntExtra("userid",0);
        postid = intent.getIntExtra("postid",1);

        //get replylist

        replylist = dbhandler.getreplylist(postid);

//      get Post object from database
        post = dbhandler.getpost(postid);

//      get game object referenced from gameid in post
        game = dbhandler.getgamefrompost(post.getGameId());

//      get post's author user from Post's UserID FK??
        User user = dbhandler.getauthorfrompost(post.getUserId());

//      concat username with post date
        tvUsernameDate.setText(user.getUsername()+ " - " + post.getPostDate());

//      get game title from post's game ID?? (yes from game object that referenced from post)
        tvGameTitle.setText(game.getTitle());

//      get post title from post ()
        tvPostTitle.setText(post.getTitle());

//      get post body from post
        tvPostBody.setText(post.getBody());

//      get reply count from post's reply arraylist.size
        if(replylist.size()>=1)
        {
            tvReplyCount.setText(String.valueOf(replylist.size()));
        }

        //setup recyclerView
        if(replylist.size()>=1)
        {
            adapter = new ReplyAdapter(replylist);
            rvReplyList.setAdapter(adapter);
            rvReplyList.setLayoutManager(new LinearLayoutManager(this));
        }

        //buttons' setOnClickListener
        btnReply.setOnClickListener(this);
        fabReply.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_post_list, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(currentuserid == post.getUserId()){
            menu.findItem(R.id.post_menu_remove).setVisible(true);
        }
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
            if(dbhandler.addbookmark(currentuserid,post.getPostId(),this)){
                Toast.makeText(this, "Post bookmarked!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "dbhandler.addbookmark return true");
            }else{
                Toast.makeText(this,"Post already bookmarked",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "dbhandler.addbookmark return false");
            }
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
        }else if(item.getItemId() == R.id.post_menu_remove){
            //call removepost(userid, postid) to remove post and all replies from postid
            //remove post from postlist arraylist in PostIndexActivity
            //notify data change to the recyclerview
            //intent to PostIndexActivity
            Intent intent = new Intent(this,PostIndexActivity.class);
            intent.putExtra("userid", currentuserid);
            intent.putExtra("gameid", game.getGameid());
            dbhandler.removepost(currentuserid,postid);
            Toast.makeText(this, "Post Removed!", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btnReply.getId() || v.getId() == fabReply.getId()){
            Intent intent = new Intent(this, ReplyActivity.class);
            //send author info and post origin to replyActivity
            intent.putExtra("userid",currentuserid);
            intent.putExtra("postid",postid);
            startActivity(intent);
            finish();
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
        btnReply = findViewById(R.id.btn_reply);
        tvReplyCount = findViewById(R.id.tv_reply_count);
        rvReplyList = findViewById(R.id.rv_reply_list);
        fabReply = findViewById(R.id.fab_reply);
        Log.i(TAG, "Views ready!");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,PostIndexActivity.class);
        intent.putExtra("userid",currentuserid);
        intent.putExtra("gameid",game.getGameid());
        startActivity(intent);
        finish();
    }
}