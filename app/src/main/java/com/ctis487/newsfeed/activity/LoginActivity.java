package com.ctis487.newsfeed.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.ctis487.newsfeed.R;
import com.ctis487.newsfeed.databinding.ActivityLoginBinding;
import com.ctis487.newsfeed.entity.User;
import com.ctis487.newsfeed.service.UserTable;
import com.ctis487.newsfeed.util.Common;
import com.ctis487.newsfeed.util.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        dbHelper = new DatabaseHelper(this);


        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = UserTable.checkPassword(dbHelper, binding.editUsername.getText().toString(), binding.editPassword.getText().toString());
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                if(user != null){
                    Common.setUser(user);
                    startActivity(intent);
                }else{
                    binding.tvValidation.setText(R.string.tvInvalidUser);
                    binding.tvValidation.setVisibility(View.VISIBLE);
                }

            }
        });

        binding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}