package com.mcs_lec_project.gamicality;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GameListActivity extends AppCompatActivity{
    ArrayList<Game> Game;
    SQLiteDatabase db;
    DBHandler dbHandler;
    int currentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        RecyclerView rv_Game = findViewById(R.id.rv_gamelist);
        GameListAdapter GameAdapter = new GameListAdapter();
        dbHandler = new DBHandler(this);

        Intent intent = getIntent();
        currentuser = intent.getIntExtra("userid",0);
        Game = dbHandler.getgamelist();

        // call RecyclerView
        GameAdapter.setData(Game, GameListActivity.this,currentuser);
        rv_Game.setAdapter(GameAdapter);
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false);
        rv_Game.setLayoutManager(gridLayoutManager);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Gamicality");
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
            intent = new Intent(this,ProfileActivity.class);
            intent.putExtra("userid",currentuser);
            startActivity(intent);
            return true;
        }else if(item.getItemId() == R.id.menu_home){
            intent = new Intent(this, GameListActivity.class);
            intent.putExtra("userid", currentuser);
            startActivity(intent);
            return true;
        }else if(item.getItemId() == R.id.menu_bookmarks){
            intent = new Intent(this, BookmarkActivity.class);
            intent.putExtra("userid", currentuser);
            startActivity(intent);
            return true;
        }else if(item.getItemId() == R.id.menu_notif){
            intent = new Intent(this, NotificationActivity.class);
            intent.putExtra("userid", currentuser);
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