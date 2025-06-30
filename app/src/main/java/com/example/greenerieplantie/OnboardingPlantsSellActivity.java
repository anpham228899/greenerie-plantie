package com.example.greenerieplantie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class OnboardingPlantsSellActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_plantsell);

        // Ẩn thanh ActionBar nếu có
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }



        // Xử lý nút Back
        ImageButton backButton = findViewById(R.id.imgbtn_onboarding_plant_sell_back);
        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(OnboardingPlantsSellActivity.this, OnboardingChatbotActivity.class);
            startActivity(intent);
        });

        // Xử lý nút Start để vào màn hình chính
        Button startButton = findViewById(R.id.btn_verify_code_reset_password);
        startButton.setOnClickListener(view -> {
            Intent intent = new Intent(OnboardingPlantsSellActivity.this, SignInActivity.class); // hoặc activity chính khác
            startActivity(intent);
            finish(); // Kết thúc onboarding
        });
    }
}

