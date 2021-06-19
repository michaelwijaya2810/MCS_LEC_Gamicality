package com.mcs_lec_project.gamicality;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    ArrayList<Notification> notificationslist;
    DBHandler dbhandler;
    int currentuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Notifications");

        dbhandler = new DBHandler(this);
        Intent intent = getIntent();
        currentuser = intent.getIntExtra("userid",0);

        notificationslist = dbhandler.getnotificationlist(currentuser);

        RecyclerView rv_notification = findViewById(R.id.rv_notification);
        NotificationAdapter notificationAdapter = new NotificationAdapter();

       // dividers
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(this.getResources().getDrawable(R.drawable.dividers));
        rv_notification.addItemDecoration(dividerItemDecoration);

        // call RecyclerView
        notificationAdapter.setData(notificationslist, NotificationActivity.this);
        rv_notification.setAdapter(notificationAdapter);
        rv_notification.setLayoutManager(new LinearLayoutManager(this));

        // swipe right to delete
        ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                notificationslist.remove(viewHolder.getAdapterPosition());
                notificationAdapter.notifyDataSetChanged();
                Toast.makeText(NotificationActivity.this, "Notification removed!", Toast.LENGTH_SHORT).show();
            }
        };
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(rv_notification);
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
            intent.putExtra("userid", currentuser);
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