package com.mcs_lec_project.gamicality;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    ArrayList<User> userlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        TextView registerhere =  findViewById(R.id.registerhere);

        Button loginbtn = findViewById(R.id.loginbtn);
        DBHandler db = new DBHandler(this);

        registerhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });




        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usernametxt = findViewById(R.id.loginuser);
                EditText passwordtxt = findViewById(R.id.loginpass);

                String username = usernametxt.getText().toString();
                String password = passwordtxt.getText().toString();

                userlist = db.getuserlist();
                int x= userlist.size()-1;
                while(x>=0)
                {
                    if(username.equals(userlist.get(x).Username)&& password.equals(userlist.get(x).password))
                    {
                        Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();



                        // intent after login to HOME
//                        /*
                        Intent intent = new Intent(Login.this,BookmarkActivity.class);
                        intent.putExtra("userid", userlist.get(x).Userid);
                        startActivity(intent);



//                         */



                    }

                    x--;

                }
                Toast.makeText(Login.this, "User not found", Toast.LENGTH_SHORT).show();

            }
        });




    }
}