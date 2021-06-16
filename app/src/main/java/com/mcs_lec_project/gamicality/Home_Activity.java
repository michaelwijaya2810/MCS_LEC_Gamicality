package com.mcs_lec_project.gamicality;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Home_Activity extends AppCompatActivity {
    ArrayList<Home> homes = new ArrayList<Home>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecyclerView rv_home = findViewById(R.id.rv_home);
       HomeAdapter homeAdapter = new HomeAdapter();
        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);

        // sample data
        Home home = new Home();
        home.setImageId(R.drawable.profile_picture3);
        home.setName("User12345");
        home.setDate("07-06-2021");
        home.setContent("Genshin Update Patch 1.6");
        homes.add(home);

        Home home1 = new Home();
        home1.setImageId(R.drawable.profile_picture1);
        home1.setName("UserGenshin");
        home1.setDate("13-06-2021");
        home1.setContent("Top 10 best DPS Character Genshin");
        homes.add(home1);

        // dividers
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(this.getResources().getDrawable(R.drawable.dividers));
        rv_home.addItemDecoration(dividerItemDecoration);

        // call RecyclerView
        homeAdapter.setData(homes, Home_Activity.this);
        rv_home.setAdapter(homeAdapter);
        rv_home.setLayoutManager(new LinearLayoutManager(this));

    //Add Button intent AddPost
    fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Home_Activity.this, AddPostActivity.class);
            startActivity(intent);
          }
        });
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
            intent = new Intent(Home_Activity.this, Home_Activity.class);
            startActivity(intent);
        }else if(item.getItemId() == R.id.menu_game_list){
            intent = new Intent(Home_Activity.this, GameList_Activity.class);
            startActivity(intent);
            return true;
        }else if(item.getItemId() == R.id.menu_bookmarks){
            intent = new Intent(Home_Activity.this, BookmarkActivity.class);
            startActivity(intent);
            return true;
        }else if(item.getItemId() == R.id.menu_notif){
            intent = new Intent(Home_Activity.this, NotificationActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}