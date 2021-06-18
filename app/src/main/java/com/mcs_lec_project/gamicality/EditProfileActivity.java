package com.mcs_lec_project.gamicality;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditProfileActivity extends AppCompatActivity {
    EditText usernameInput;
    EditText emailInput;
    EditText passwordInput;
    EditText confirmPasswordInput;
    Button saveBtn;
    int currentuser;
    DBHandler dbhandler;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbhandler = new DBHandler(this);
        setContentView(R.layout.activity_edit_profile);



        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.back_icon);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Edit Profile");
        Intent intent =getIntent();
        currentuser = intent.getIntExtra("userid",0);
        user = dbhandler.getuserinformation(currentuser);
        setupViews();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getEditableText().toString();
                String email = emailInput.getEditableText().toString();
                String password = passwordInput.getEditableText().toString();
                String confirmPassword = confirmPasswordInput.getEditableText().toString();

                //hanya ubah informasi yang diisi user (ex. jika user isi semua editText kecuali email, ganti username sama password (jika berbeda) aja)
                if(password.isEmpty()){
                    passwordInput.setError("Either use old/new password!");
                }else if(confirmPassword.isEmpty()){
                    confirmPasswordInput.setError("Confirm your password!");
                }else if(!confirmPassword.equals(password)){
                    confirmPasswordInput.setError("Password does not match!");
                }else{
                    //update user's information
                    dbhandler.edituserprofile(username,password,email,currentuser);
                    Intent intent1 = new Intent(EditProfileActivity.this,ProfileActivity.class);
                    intent1.putExtra("userid",currentuser);
                    startActivity(intent1);
                    Toast.makeText(EditProfileActivity.this, "Account Info Updated!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void setupViews(){


        usernameInput = findViewById(R.id.UsernameInput);
        usernameInput.setText(user.getUsername());

        emailInput = findViewById(R.id.EmailInput);
        emailInput.setText(user.getEmail());

        passwordInput = findViewById(R.id.PasswordInput);
        passwordInput.setText(user.getPassword());

        confirmPasswordInput = findViewById(R.id.ConfirmPasswordInput);
        saveBtn = findViewById(R.id.Savebtn);
    }
}
