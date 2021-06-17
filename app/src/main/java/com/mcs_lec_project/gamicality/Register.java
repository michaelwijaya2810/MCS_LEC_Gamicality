package com.mcs_lec_project.gamicality;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Register extends AppCompatActivity {
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        DBHandler dbhandler = new DBHandler(this);
        Button registerbtn = findViewById(R.id.RegisterBtn);



        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usernametxt = findViewById(R.id.Regisusername);
                EditText passwordtxt = findViewById(R.id.RegisPass);
                EditText passwordconfirmtxt = findViewById(R.id.RegisPassconfirm);
                EditText emailtxt = findViewById(R.id.RegisEmail);


                String username = usernametxt.getText().toString();
                String password = passwordtxt.getText().toString();
                String passwordconfirm = passwordconfirmtxt.getText().toString();
                String email = emailtxt.getText().toString();

                if(!password.equals(passwordconfirm))
                {
                    Toast.makeText(context, "password does not match",Toast.LENGTH_SHORT).show();
                }
                else if(password.isEmpty())
                {
                    Toast.makeText(context, "password cannot be empty",Toast.LENGTH_SHORT).show();
                }
                else if (username.isEmpty())
                {
                    Toast.makeText(context, "Username cannot be empty",Toast.LENGTH_SHORT).show();
                }
                else if(email.isEmpty())
                {
                    Toast.makeText(context, "email cannot be empty",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    dbhandler.insertuser(username,password,email);
                    Intent intent = new Intent(Register.this,Login.class);
                    startActivity(intent);
                    Toast.makeText(context, "user created",Toast.LENGTH_SHORT).show();
                }
            }
        });





    }
}