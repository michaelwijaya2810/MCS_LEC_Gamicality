package com.mcs_lec_project.gamicality;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class BookmarkActivity extends AppCompatActivity implements BookmarkAdapter.OnBookmarkListener {

    ArrayList<Bookmark> bookmarklist;
    DBHandler dbhandler;
    int currentuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        RecyclerView rv_bookmark = findViewById(R.id.rv_bookmark);
        BookmarkAdapter bookmarkAdapter = new BookmarkAdapter();
        dbhandler = new DBHandler(this);
        Intent intent = getIntent();
        currentuser = intent.getIntExtra("userid",0);

        bookmarklist = dbhandler.getbookmarklist(currentuser);

        // dividers
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(this.getResources().getDrawable(R.drawable.dividers));
        rv_bookmark.addItemDecoration(dividerItemDecoration);

        // call RecyclerView
        if(bookmarklist.size()>=1)
        {
            bookmarkAdapter.setData(bookmarklist, BookmarkActivity.this, this);
            rv_bookmark.setAdapter(bookmarkAdapter);
            rv_bookmark.setLayoutManager(new LinearLayoutManager(this));
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Bookmarks");

    }

    @Override
    public void OnBookmarkClick(int position) {
        Intent intent = new Intent(BookmarkActivity.this, PostDetailActivity.class);
        intent.putExtra("userid",currentuser);
        intent.putExtra("postid",bookmarklist.get(position).getPostid());
        startActivity(intent);
    }

    // Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
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
}