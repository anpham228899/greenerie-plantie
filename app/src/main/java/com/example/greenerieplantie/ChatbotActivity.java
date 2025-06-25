package com.example.greenerieplantie;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ChatbotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chatbot);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        NavMenuActivity.setupNavMenu(bottomNav, this, R.id.nav_chatbot); // ID này phải trùng trong menu XML
        bottomNav.setSelectedItemId(R.id.nav_chatbot);

    }
}