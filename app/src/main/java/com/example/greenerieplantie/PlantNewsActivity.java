package com.example.greenerieplantie;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import adapters.NewsDetailAdapter;
import models.NewsDetail;

public class PlantNewsActivity extends AppCompatActivity {

    private RecyclerView newsRecyclerView;
    private NewsDetailAdapter adapter;
    private List<NewsDetail> newsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_plant_news);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.plant_news), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        newsRecyclerView = findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsDetailAdapter(newsList, this);
        newsRecyclerView.setAdapter(adapter);

        loadNewsFromFirebase();
    }

    private void loadNewsFromFirebase() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("newsDetails");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                newsList.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    NewsDetail news = child.getValue(NewsDetail.class);
                    if (news != null) {
                        newsList.add(news);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("Firebase", "Failed to load news: " + error.getMessage());
            }
        });
    }
}
