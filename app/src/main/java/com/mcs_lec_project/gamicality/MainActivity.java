package com.mcs_lec_project.gamicality;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    int currentuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentuser = intent.getIntExtra("userid",0);

    }

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
            intent = new Intent(MainActivity.this, MainActivity.class);
            intent.putExtra("userid",currentuser);
            startActivity(intent);
            return true;
        }else if(item.getItemId() == R.id.menu_game_list){
            intent = new Intent(MainActivity.this, GameListActivity.class);
            intent.putExtra("userid",currentuser);
            startActivity(intent);
            return true;
        }else if(item.getItemId() == R.id.menu_bookmarks){
            intent = new Intent(MainActivity.this, BookmarkActivity.class);
            intent.putExtra("userid",currentuser);
            startActivity(intent);
            return true;
        }else if(item.getItemId() == R.id.menu_notif){
            intent = new Intent(MainActivity.this, NotificationActivity.class);
            intent.putExtra("userid",currentuser);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}