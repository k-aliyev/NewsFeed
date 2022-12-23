package com.ctis487.newsfeed.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.ctis487.newsfeed.R;
import com.ctis487.newsfeed.databinding.ActivityRegisterBinding;
import com.ctis487.newsfeed.service.UserTable;
import com.ctis487.newsfeed.util.DatabaseHelper;


public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        dbHelper = new DatabaseHelper(this);

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = binding.editUsername.getText().toString();
                String password = binding.editPassword.getText().toString();
                String email = binding.editEmail.getText().toString();
                String repeat = binding.editConfirmPassword.getText().toString();

                if(!password.equals(repeat)) {
                    Toast.makeText(RegisterActivity.this, "Password should be the same", Toast.LENGTH_SHORT).show();
                }else if(!UserTable.isUsernameAvailable(dbHelper, username)){
                    Toast.makeText(RegisterActivity.this, "This username already exists", Toast.LENGTH_SHORT).show();
                }
                else if(!UserTable.isEmailAvailable(dbHelper, email)){
                    Toast.makeText(RegisterActivity.this, "This email already exists", Toast.LENGTH_SHORT).show();
                }
                else{
                    UserTable.insertUser(dbHelper, username, email, password);
                    finish();
                }
            }
        });

    }
}