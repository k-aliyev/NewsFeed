package com.ctis487.newsfeed.service;


import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ctis487.newsfeed.R;
import com.ctis487.newsfeed.activity.MainActivity;
import com.ctis487.newsfeed.entity.Article;
import com.ctis487.newsfeed.entity.Source;
import com.ctis487.newsfeed.entity.UserSource;
import com.ctis487.newsfeed.util.Common;
import com.ctis487.newsfeed.util.DatabaseHelper;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.CustomRecyclerAdapterHolder> {

    private Context context;
    private ArrayList<Article> articles;
    GestureDetectorCompat gestureDetector;
    CustomGestureListener customGestureListener;
    DatabaseHelper dbHelper;
    Source source = new Source();

    public CustomRecyclerViewAdapter(Context context, ArrayList<Article> values) {
        this.context = context;
        this.articles = values;

        customGestureListener = new CustomGestureListener();
        gestureDetector = new GestureDetectorCompat(context, customGestureListener);
        dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public CustomRecyclerAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.recyclerview, parent, false);

        return new CustomRecyclerAdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomRecyclerAdapterHolder holder, int position) {
        final Article item = articles.get(position);
        holder.news_title.setText(item.getTitle());
        holder.news_subtitle.setText(item.getDescription());

        Picasso.get()
                .load(item.getUrlToImage())
                .into(holder.news_image);

        holder.parentItemLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                source = item.getSource();
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return articles != null ? articles.size() : 0;
    }

    public class CustomRecyclerAdapterHolder extends RecyclerView.ViewHolder{

        TextView news_title, news_subtitle;
        ImageView news_image;
        MaterialCardView parentItemLayout;

        public CustomRecyclerAdapterHolder(@NonNull View itemView) {
            super(itemView);

            news_title = itemView.findViewById(R.id.news_title);
            news_subtitle = itemView.findViewById(R.id.news_subtitle);
            news_image = itemView.findViewById(R.id.news_image);
            parentItemLayout = itemView.findViewById(R.id.card_view);
        }
    }

    class CustomGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            if(!source.getId().isEmpty()) {
                UserSourceTable.insertUser(dbHelper, new UserSource(Common.user.getId(), source.getId()));
                Toast.makeText(context, "You are now subscribed to " + source.getName(), Toast.LENGTH_LONG).show();
            }

        }
    }
}