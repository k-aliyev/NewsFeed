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
                    showValidation("Password should be the same");
                }else if(!UserTable.isUsernameAvailable(dbHelper, username)){
                    showValidation("This username already exists");
                }
                else if(!UserTable.isEmailAvailable(dbHelper, email)){
                    showValidation("This email already exists");
                }
                else{
                    UserTable.insertUser(dbHelper, username, email, password);
                    finish();
                }
            }
        });

    }

    private void showValidation(String message){
        binding.tvValidation.setText(message);
        binding.tvValidation.setVisibility(View.VISIBLE);
    }
}