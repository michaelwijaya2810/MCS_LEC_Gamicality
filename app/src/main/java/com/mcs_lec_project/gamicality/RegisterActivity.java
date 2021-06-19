package com.mcs_lec_project.gamicality;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        DBHandler dbhandler = new DBHandler(this);
        Button registerbtn = findViewById(R.id.RegisterBtn);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usernametxt = findViewById(R.id.Regisusername);
                EditText passwordtxt = findViewById(R.id.RegisPass);
                EditText passwordconfirmtxt = findViewById(R.id.RegisPassconfirm);
                EditText emailtxt = findViewById(R.id.RegisEmail);

                String username = usernametxt.getEditableText().toString();
                String password = passwordtxt.getEditableText().toString();
                String passwordconfirm = passwordconfirmtxt.getEditableText().toString();
                String email = emailtxt.getEditableText().toString();

                if (username.isEmpty())
                {
                    usernametxt.setError("Username is empty");
                }
                else if(email.isEmpty())
                {
                    emailtxt.setError("Email is empty");
                }
                else if(password.isEmpty())
                {
                    passwordtxt.setError("Password is empty");
                }
                else if(passwordconfirm.isEmpty())
                {
                    passwordconfirmtxt.setError("Confirm your password!");
                }
                else if(!passwordconfirm.equals(password))
                {
                    passwordconfirmtxt.setError("Password does not match");
                }
                else
                {
                    dbhandler.insertuser(username,password,email);
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    Toast.makeText(context, "Account Registered!",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        });
    }
}