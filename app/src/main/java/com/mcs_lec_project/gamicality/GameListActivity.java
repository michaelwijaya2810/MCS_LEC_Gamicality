package com.mcs_lec_project.gamicality;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GameListActivity extends AppCompatActivity{
    ArrayList<Game> Game = new ArrayList<>();
    SQLiteDatabase db;
    DBHelper  dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        RecyclerView rv_Game = findViewById(R.id.rv_gamelist);
        GameListAdapter GameAdapter = new GameListAdapter();
        dbhelper = new DBHelper(this);
        db = dbhelper.getWritableDatabase();

        String[] gameName = {"Cyberpunk 2077", "Call of Duty Black Ops Cold War", "Demon Souls", "Spider-Man Miles Morales", "Immortals Fenyx Rising", "Assassin's Creed Valhalla", "Watch Dogs Legion", "Genshin Impact", "Ghostrunner"};
        int[] gameImgId = {R.drawable.cyberpunk1, R.drawable.blackopscoldwar2, R.drawable.demonsouls3, R.drawable.spidermanmiles4, R.drawable.immortalsfenyx5, R.drawable.acvalhalla6, R.drawable.watchdogslegion7, R.drawable.genshinimpact8, R.drawable.ghostrunner9};

        for (int i=0;i<9;++i){
            Game gameInfo = new Game();
            String title = gameName[i];
            int imgId = gameImgId[i];
//            db.execSQL("Insert into Games(title) values('"+title+"')");
            gameInfo.setTitle(title);
            gameInfo.setImageId(imgId);
            Game.add(gameInfo);
        }


        // call RecyclerView
        GameAdapter.setData(Game, GameListActivity.this);
        rv_Game.setAdapter(GameAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false);
        rv_Game.setLayoutManager(gridLayoutManager);

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
            intent = new Intent(GameListActivity.this, PostIndexActivity.class);
            startActivity(intent);
            return true;
        }else if(item.getItemId() == R.id.menu_game_list){
            intent = new Intent(GameListActivity.this, GameListActivity.class);
            startActivity(intent);
            return true;
        }else if(item.getItemId() == R.id.menu_bookmarks){
            intent = new Intent(GameListActivity.this, BookmarkActivity.class);
            startActivity(intent);
            return true;
        }else if(item.getItemId() == R.id.menu_notif){
            intent = new Intent(GameListActivity.this, NotificationActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}