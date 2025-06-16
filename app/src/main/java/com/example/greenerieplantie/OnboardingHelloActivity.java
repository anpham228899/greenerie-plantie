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

public class OnboardingHelloActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_hello);

        // Ẩn ActionBar nếu có
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Thiết lập định dạng chữ "Greenery"
        TextView textView = findViewById(R.id.title_onboarding_hello_welcome);
        String fullText = "Welcome to Greenery";
        int start = fullText.indexOf("Greenery");
        int end = start + "Greenery".length();

        SpannableString spannable = new SpannableString(fullText);
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#517B2C")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannable);

        // Điều hướng sang màn hình kế tiếp
        ImageButton nextButton = findViewById(R.id.imgbtn_onboarding_hello_next);

        nextButton.setOnClickListener(view -> {
            Intent intent = new Intent(OnboardingHelloActivity.this, OnboardingPlantSellActivity.class);
            startActivity(intent);
        });
    }
}
