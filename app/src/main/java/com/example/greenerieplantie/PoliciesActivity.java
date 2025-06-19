package com.example.greenerieplantie;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PoliciesActivity extends AppCompatActivity {
    private ImageButton backButton;  // Sử dụng ImageButton thay vì ImageView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ẩn thanh ActionBar nếu có
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_policies);



        // Xử lý sự kiện click button quay lại
        backButton = findViewById(R.id.btn_policy_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại màn hình trước
                onBackPressed();
            }
        });
    }

    private void boldText(SpannableStringBuilder builder, String fullText, String textToBold) {
        int start = fullText.indexOf(textToBold);
        if (start >= 0) {
            builder.setSpan(new StyleSpan(Typeface.BOLD),
                    start,
                    start + textToBold.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }
}

