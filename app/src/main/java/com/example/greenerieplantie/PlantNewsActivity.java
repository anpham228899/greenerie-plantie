package com.example.greenerieplantie;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import adapters.NewsDetailAdapter;
import models.NewsDetail;
import utils.ListNewsDetail;

public class PlantNewsActivity extends AppCompatActivity {

    private RecyclerView newsRecyclerView;
    private NewsDetailAdapter adapter;
    private List<NewsDetail> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_plant_news);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        newsRecyclerView = findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        newsList = ListNewsDetail.getSampleNews();
        adapter = new NewsDetailAdapter(newsList, this);
        newsRecyclerView.setAdapter(adapter);
    }
}
