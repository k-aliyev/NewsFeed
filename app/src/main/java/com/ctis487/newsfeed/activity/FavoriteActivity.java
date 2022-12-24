package com.ctis487.newsfeed.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.ctis487.newsfeed.R;
import com.ctis487.newsfeed.databinding.ActivityFavoriteBinding;
import com.ctis487.newsfeed.databinding.ActivityMainBinding;
import com.ctis487.newsfeed.entity.Article;
import com.ctis487.newsfeed.service.CustomRecyclerViewAdapter;
import com.ctis487.newsfeed.util.Common;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FavoriteActivity extends AppCompatActivity {

    ActivityFavoriteBinding binding;
    CustomRecyclerViewAdapter adapter;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        adapter = new CustomRecyclerViewAdapter(FavoriteActivity.this,
                new ArrayList<Article>(Common.getArticles().stream().filter(
                        article -> Common.favSourcesId.contains(
                            article.getSource().getName())).collect(Collectors.toList())), true);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerFavView.setLayoutManager(layoutManager);
        binding.recyclerFavView.setAdapter(adapter);

    }
}