package com.mcs_lec_project.gamicality;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddPostActivity extends AppCompatActivity {
    private static final String TAG = AddPostActivity.class.getSimpleName();

    private EditText etTitle;
    private EditText etBody;
    DBHandler db;
    Intent intent;
    int userid;
    int gameid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new DBHandler(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.cancel_icon);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        etTitle = findViewById(R.id.et_title);
        etBody = findViewById(R.id.et_body);

        //get intent from game selected for gameid
        intent = getIntent();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_single_action_button, menu);
        menu.findItem(R.id.menu_btn_post).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_btn_post){
            String title = etTitle.getEditableText().toString();
            String body = etBody.getEditableText().toString();

            if(!title.isEmpty()){
                if(!body.isEmpty()){
//                    get the author's info

                    //menunggu intent dari userid dan game id dari home_activity
                    userid = intent.getIntExtra("userid",0);
                    gameid = intent.getIntExtra("gameid",0);


//                    insert new post to DB
                    db.addPost(userid,gameid,title,body);
                    Toast.makeText(this, "Post created!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this,PostIndexActivity.class);
                    intent.putExtra("userid",userid);
                    intent.putExtra("gameid",gameid);
                    startActivity(intent);
                    finish();

                }else{
                    etBody.setError("Post body is empty!");
                }
            }else{
                etTitle.setError("Title is empty!");
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,PostIndexActivity.class);
        intent.putExtra("userid",userid);
        intent.putExtra("gameid",gameid);
        startActivity(intent);
        finish();
    }
}