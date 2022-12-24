package com.ctis487.newsfeed.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.core.view.GestureDetectorCompat;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.ctis487.newsfeed.R;
import com.ctis487.newsfeed.databinding.ActivityMainBinding;
import com.ctis487.newsfeed.service.ApiService;
import com.ctis487.newsfeed.service.CustomRecyclerViewAdapter;
import com.ctis487.newsfeed.service.MyVolleyUsage;
import com.ctis487.newsfeed.service.UserSourceTable;
import com.ctis487.newsfeed.util.Common;
import com.ctis487.newsfeed.util.DatabaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    CustomRecyclerViewAdapter adapter;
    ActivityMainBinding binding;
    LinearLayoutManager layoutManager;
    MyVolleyUsage myVolley;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        dbHelper = new DatabaseHelper(this);
        myVolley = new MyVolleyUsage(this);
        apiService = new ApiService();
        apiService.updateLanguageAPI();

        Common.favSourcesId = UserSourceTable.getFavoritesId(dbHelper, Common.user.getId());

        myVolley.requestArticles(apiService.nextPage());
        myVolley.requestSources(Common.SOURCES_API);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        adapter = new CustomRecyclerViewAdapter(MainActivity.this, Common.getArticles(), false);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Common.page = 0;
                        Common.clearArticles();
                        apiService.updateLanguageAPI();
                        myVolley.requestArticles(apiService.nextPage());
                        adapter.notifyDataSetChanged();
                    }
                });

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    myVolley.requestArticles(apiService.nextPage());
                    adapter.notifyDataSetChanged();

                    Log.d("Articles", Common.articles.toString());

                    if(Common.getArticles().size() > 0){
                        Toast.makeText(MainActivity.this,R.string.no_results_available, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        binding.editSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Common.search = binding.editSearch.getText().toString();
                    Common.page = 0;
                    Common.clearArticles();

                    myVolley.requestArticles(apiService.nextPage());
                    adapter.notifyDataSetChanged();

                    if(Common.getArticles().size() > 0){
                        Toast.makeText(MainActivity.this,R.string.no_results_available, Toast.LENGTH_LONG).show();
                    }

                    return true;
                }
                return false;
            }
        });

        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.settings){
                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    someActivityResultLauncher.launch(intent);
                    return true;
                }else if(item.getItemId() == R.id.favorites){
                    Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });



    }

}