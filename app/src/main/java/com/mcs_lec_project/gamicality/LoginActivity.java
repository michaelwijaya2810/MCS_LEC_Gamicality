package com.mcs_lec_project.gamicality;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    ArrayList<User> userlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView registerhere =  findViewById(R.id.registerhere);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");

        Button loginbtn = findViewById(R.id.loginbtn);
        DBHandler db = new DBHandler(this);

        registerhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usernametxt = findViewById(R.id.loginuser);
                EditText passwordtxt = findViewById(R.id.loginpass);

                String username = usernametxt.getEditableText().toString();
                String password = passwordtxt.getEditableText().toString();

                if(username.isEmpty()){
                    usernametxt.setError("Username is empty!");
                }else if(password.isEmpty()){
                    passwordtxt.setError("Password is empty!");
                }else{
                    userlist = db.getuserlist();
                    int x= userlist.size()-1;
                    while(x>=0)
                    {
                        if(username.equals(userlist.get(x).Username)&& password.equals(userlist.get(x).password))
                        {
                            Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            // intent after login to HOME
                            Intent intent = new Intent(LoginActivity.this,GameListActivity.class);
                            intent.putExtra("userid", userlist.get(x).Userid);
                            startActivity(intent);
                            finish();
                            break;
                        }
                        x--;
                    }
                    if(x < 0){
                        Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}