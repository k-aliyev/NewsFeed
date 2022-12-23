package com.ctis487.newsfeed.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.ctis487.newsfeed.databinding.ActivityMainBinding;
import com.ctis487.newsfeed.databinding.ActivitySettingsBinding;
import com.ctis487.newsfeed.service.ApiService;
import com.ctis487.newsfeed.service.CustomRecyclerViewAdapter;
import com.ctis487.newsfeed.service.MyVolleyUsage;
import com.ctis487.newsfeed.service.UserTable;
import com.ctis487.newsfeed.util.Common;
import com.ctis487.newsfeed.util.DatabaseHelper;

public class SettingsActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    ActivitySettingsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        dbHelper = new DatabaseHelper(this);

        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.editUsername.getText().toString();
                String password = binding.editPassword.getText().toString();
                String email = binding.editEmail.getText().toString();

                if(!password.equals(Common.user.getPassword())) {
                    Toast.makeText(SettingsActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                }else if(!UserTable.isUsernameAvailable(dbHelper, username)){
                    Toast.makeText(SettingsActivity.this, "This username already exists", Toast.LENGTH_SHORT).show();
                }
                else if(!UserTable.isEmailAvailable(dbHelper, email)){
                    Toast.makeText(SettingsActivity.this, "This email already exists", Toast.LENGTH_SHORT).show();
                }
                else{
                    UserTable.updateUser(dbHelper, Common.user.getId(), username, email, password);
                    finish();

                    Toast.makeText(SettingsActivity.this, "New settings saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Common.language = binding.spinner.getSelectedItem().toString();
                Toast.makeText(SettingsActivity.this, "Language changes to " + Common.language, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}