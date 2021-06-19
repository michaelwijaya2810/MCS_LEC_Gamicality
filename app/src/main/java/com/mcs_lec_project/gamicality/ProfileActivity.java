package com.mcs_lec_project.gamicality;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    Intent intent;

    TextView username;
    TextView postCount;
    RecyclerView profileRv;
    int currentuser;
    DBHandler dbhandler;
    //pake Post atau Home?
    ArrayList<Post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        dbhandler = new DBHandler(this);

        currentuser = intent.getIntExtra("userid",0);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.back_icon);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Profile");

        username = findViewById(R.id.profilename1);
        postCount = findViewById(R.id.postcount);
        profileRv = findViewById(R.id.profilerecycleview);
        User user = dbhandler.getcurrentuser(currentuser);

        username.setText(user.getUsername());
        postCount.setText(String.valueOf(dbhandler.getuserpostcount(currentuser))+" Post Count");

        postList = dbhandler.getuserpostlist(currentuser);

        ProfileAdapter adapter = new ProfileAdapter(this, postList,currentuser);
        profileRv.setAdapter(adapter);
        profileRv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_single_action_button, menu);
        menu.findItem(R.id.menu_btn_edit_profile).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_btn_edit_profile){
            intent = new Intent(this, EditProfileActivity.class);
            intent.putExtra("userid",currentuser);
            startActivity(intent);
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
//        Intent intent = new Intent(this, GameListActivity.class);
//        intent.putExtra("userid",currentuser);
//        startActivity(intent);
        finish();
    }
}
