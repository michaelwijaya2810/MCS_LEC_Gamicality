package com.mcs_lec_project.gamicality;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditProfileActivity extends AppCompatActivity {
    EditText usernameInput;
    EditText emailInput;
    EditText passwordInput;
    EditText confirmPasswordInput;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        setupViews();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.back_icon);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Edit Profile");

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
        emailInput = findViewById(R.id.EmailInput);
        passwordInput = findViewById(R.id.PasswordInput);
        confirmPasswordInput = findViewById(R.id.ConfirmPasswordInput);
        saveBtn = findViewById(R.id.Savebtn);
    }
}
