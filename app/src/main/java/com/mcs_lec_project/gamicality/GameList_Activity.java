package com.mcs_lec_project.gamicality;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GameList_Activity extends AppCompatActivity{
    ArrayList<GameList> gamelist = new ArrayList<GameList>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        RecyclerView rv_gamelist = findViewById(R.id.rv_gamelist);
        GameListAdapter gamelistAdapter = new GameListAdapter();

        // sample data
        GameList game = new GameList();
        game.setImageId(R.drawable.profile_picture2);
        game.setName("Anos Voldigoad");
        gamelist.add(game);

        GameList game1 = new GameList();
        game1.setImageId(R.drawable.profile_picture3);
        game1.setName("Fox");
        gamelist.add(game1);

        GameList game2 = new GameList();
        game2.setImageId(R.drawable.profile_picture2);
        game2.setName("Misfit Academy");
        gamelist.add(game2);

        GameList game3 = new GameList();
        game3.setImageId(R.drawable.profile_picture1);
        game3.setName("Genshin Impact");
        gamelist.add(game3);

        GameList game4 = new GameList();
        game4.setImageId(R.drawable.profile_picture3);
        game4.setName("Fox Test");
        gamelist.add(game4);

        GameList game5 = new GameList();
        game5.setImageId(R.drawable.profile_picture1);
        game5.setName("Amber Mondaldst");
        gamelist.add(game5);

        GameList game6 = new GameList();
        game6.setImageId(R.drawable.cyberpunnk);
        game6.setName("Avos Dilhevia Academy");
        gamelist.add(game6);

        GameList game7 = new GameList();
        game7.setImageId(R.drawable.cyberpunnk);
        game7.setName("Avos Dilhevia");
        gamelist.add(game7);

        GameList game8 = new GameList();
        game8.setImageId(R.drawable.profile_picture2);
        game8.setName("Avos Dilhevia");
        gamelist.add(game8);

        // call RecyclerView
        gamelistAdapter.setData(gamelist, GameList_Activity.this);
        rv_gamelist.setAdapter(gamelistAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false);
        rv_gamelist.setLayoutManager(gridLayoutManager);

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
            intent = new Intent(GameList_Activity.this, Home_Activity.class);
            startActivity(intent);
        }else if(item.getItemId() == R.id.menu_game_list){
            intent = new Intent(GameList_Activity.this, GameList_Activity.class);
            startActivity(intent);
            return true;
        }else if(item.getItemId() == R.id.menu_bookmarks){
            intent = new Intent(GameList_Activity.this, BookmarkActivity.class);
            startActivity(intent);
            return true;
        }else if(item.getItemId() == R.id.menu_notif){
            intent = new Intent(GameList_Activity.this, NotificationActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}