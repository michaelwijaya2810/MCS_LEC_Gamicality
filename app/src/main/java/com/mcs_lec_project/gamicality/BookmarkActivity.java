package com.mcs_lec_project.gamicality;

import androidx.annotation.NonNull;
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

    ArrayList<Bookmark> bookmarks = new ArrayList<Bookmark>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        RecyclerView rv_bookmark = findViewById(R.id.rv_bookmark);
        BookmarkAdapter bookmarkAdapter = new BookmarkAdapter();

        // sample data
        Bookmark bookmark = new Bookmark();
        bookmark.setImageId(R.drawable.profile_picture3);
        bookmark.setContent("Hunter's Fury Build - turn every SMG to monster");
        bookmarks.add(bookmark);

        Bookmark bookmark1 = new Bookmark();
        bookmark1.setImageId(R.drawable.profile_picture1);
        bookmark1.setContent("Genshin Impact Fan Club - top 10 best character");
        bookmarks.add(bookmark1);

        // dividers
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(this.getResources().getDrawable(R.drawable.dividers));
        rv_bookmark.addItemDecoration(dividerItemDecoration);

        // call RecyclerView
        bookmarkAdapter.setData(bookmarks, BookmarkActivity.this, this);
        rv_bookmark.setAdapter(bookmarkAdapter);
        rv_bookmark.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void OnBookmarkClick(int position) {
        Intent intent = new Intent(BookmarkActivity.this, PostDummy.class);
        startActivity(intent);
    }

    // Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent();
        if(item.getItemId() == R.id.action_profile){
            return true;
        }else if(item.getItemId() == R.id.menu_home){
            intent = new Intent(BookmarkActivity.this, MainActivity.class);
            startActivity(intent);
        }else if(item.getItemId() == R.id.menu_game_list){
            return true;
        }else if(item.getItemId() == R.id.menu_bookmarks){
            intent = new Intent(this, BookmarkActivity.class);
            startActivity(intent);
            return true;
        }else if(item.getItemId() == R.id.menu_notif){
            intent = new Intent(this, NotificationActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}