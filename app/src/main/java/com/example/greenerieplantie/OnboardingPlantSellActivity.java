package com.example.greenerieplantie;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OnboardingPlantSellActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_plant_sell);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        TextView textView = findViewById(R.id.title_onboarding_plant_sell_welcome);

        // Tạo nội dung văn bản
        String fullText = "Meet Plantie - Your AI Assistant";

        // Tìm vị trí bắt đầu và kết thúc của từ "Greenery"
        int start = fullText.indexOf("Plantie");
        int end = start + "Plantie".length();

        // Xử lý điều hướng qua lại
        ImageButton backButton = findViewById(R.id.imgbtn_onboarding_plant_sell_back);
        ImageButton nextButton = findViewById(R.id.imgbtn_onboarding_plant_sell_next);

        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(OnboardingPlantSellActivity.this, OnboardingHelloActivity.class);
            startActivity(intent);

        });

        nextButton.setOnClickListener(view -> {
            Intent intent = new Intent(OnboardingPlantSellActivity.this, OnboardingChatbotActivity.class);
            startActivity(intent);

        });
    }
}