package com.mcs_lec_project.gamicality;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PostIndexActivity extends AppCompatActivity {
    ArrayList<Home> postlist;
    int currentuser;
    int currentgameid;
    DBHandler dbhandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_index);
        dbhandler = new DBHandler(this);
        RecyclerView rv_home = findViewById(R.id.rv_home);
        HomeAdapter homeAdapter = new HomeAdapter();
        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        Intent intent = getIntent();
        currentuser = intent.getIntExtra("userid",0);
        currentgameid = intent.getIntExtra("gameid",0);

        postlist = dbhandler.getgamepostlist(currentgameid);

        // dividers
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(this.getResources().getDrawable(R.drawable.dividers));
        rv_home.addItemDecoration(dividerItemDecoration);

        // call RecyclerView
        if(postlist.size()>=1)
        {
            homeAdapter.setData(postlist, PostIndexActivity.this,currentuser);
            rv_home.setAdapter(homeAdapter);
            rv_home.setLayoutManager(new LinearLayoutManager(this));
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.back_icon);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Post List");

    //Add Button intent AddPost
    fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(PostIndexActivity.this, AddPostActivity.class);
            intent.putExtra("userid",currentuser);
            intent.putExtra("gameid",currentgameid);
            startActivity(intent);
          }
        });
    }

    // Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent();
        if(item.getItemId() == R.id.action_profile){
            intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("userid",currentuser);
            startActivity(intent);
            return true;
        }else if(item.getItemId() == R.id.menu_home){
            intent = new Intent(this, GameListActivity.class);
            intent.putExtra("userid",currentuser);
            startActivity(intent);
            return true;
        }else if(item.getItemId() == R.id.menu_bookmarks){
            intent = new Intent(this, BookmarkActivity.class);
            intent.putExtra("userid",currentuser);
            startActivity(intent);
            return true;
        }else if(item.getItemId() == R.id.menu_notif){
            intent = new Intent(this, NotificationActivity.class);
            intent.putExtra("userid",currentuser);
            startActivity(intent);
            return true;
        }else if(item.getItemId() == R.id.menu_logout){
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, GameListActivity.class);
        intent.putExtra("userid",currentuser);
        startActivity(intent);
    }
}