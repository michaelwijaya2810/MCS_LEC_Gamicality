package com.mcs_lec_project.gamicality;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    ArrayList<Notification> notificationslist;
    DBHandler dbhandler;
    int currentuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        dbhandler = new DBHandler(this);
        Intent intent = getIntent();
        currentuser = intent.getIntExtra("userid",0);

        notificationslist = dbhandler.getnotificationlist(currentuser);

        RecyclerView rv_notification = findViewById(R.id.rv_notification);
        NotificationAdapter notificationAdapter = new NotificationAdapter();

        // sample data
//        Notification notification = new Notification();
//        notification.setImageId(R.drawable.profile_picture1);
//        notification.setContent("ruthless_kitty liked your reply!");
//        notifications.add(notification);
//
//        Notification notification1 = new Notification();
//        notification1.setImageId(R.drawable.profile_picture1);
//        notification1.setContent("just_a_random_dude replied to your post your post!");
//        notifications.add(notification1);
//
//        Notification notification2 = new Notification();
//        notification2.setImageId(R.drawable.profile_picture1);
//        notification2.setContent("Genshin Impact fan Club posted a new article!");
//        notifications.add(notification2);

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
            }
        };
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(rv_notification);
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
            intent = new Intent(NotificationActivity.this, MainActivity.class);
            intent.putExtra("userid",currentuser);
            startActivity(intent);
        }else if(item.getItemId() == R.id.menu_game_list){
            intent = new Intent(NotificationActivity.this, BookmarkActivity.class);
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
        }
        return super.onOptionsItemSelected(item);
    }
}