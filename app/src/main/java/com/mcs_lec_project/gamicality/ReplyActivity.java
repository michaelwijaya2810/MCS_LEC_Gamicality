package com.mcs_lec_project.gamicality;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class ReplyActivity extends AppCompatActivity {
    private static final String TAG = ReplyActivity.class.getSimpleName();

    private EditText etBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        setupActionBar();

        etBody = findViewById(R.id.et_body);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_post_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_btn_post){
            String body = etBody.getEditableText().toString();

            if(!body.isEmpty()){
//              get the author's info
//              insert new reply to DB
                Toast.makeText(this, "You replied to the post!", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                etBody.setError("Reply body is empty!");
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.cancel_icon);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
    }
}