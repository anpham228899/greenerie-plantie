package com.example.greenerieplantie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import utils.ListAboutUs;
import models.AboutUs;

import java.util.ArrayList;

public class AboutUsActivity extends AppCompatActivity {

    private ImageView btnBack;
    private TextView tvAboutUsContent1, tvAboutUsContent2, tvAboutUsContent3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        // Xử lý nút quay lại
        btnBack = findViewById(R.id.btnBack);
        setUpBackButton();

        // Liên kết các TextView với XML
        tvAboutUsContent1 = findViewById(R.id.tv_about_us_content_1);
        tvAboutUsContent2 = findViewById(R.id.tv_about_us_content_2);
        tvAboutUsContent3 = findViewById(R.id.tv_about_us_content_3);

        // Thiết lập nội dung cho các TextView từ dữ liệu giả lập
        ArrayList<AboutUs> aboutUsList = ListAboutUs.getAboutUsList(); // Lấy dữ liệu từ ListAboutUs

        tvAboutUsContent1.setText(aboutUsList.get(0).getContent());
        tvAboutUsContent2.setText(aboutUsList.get(1).getContent());
        tvAboutUsContent3.setText(aboutUsList.get(2).getContent());
    }

    // Hàm thiết lập sự kiện cho nút quay lại
    private void setUpBackButton() {
        btnBack.setOnClickListener(v -> {
            // Quay về màn hình Profile Management khi nhấn nút Back
            Intent intent = new Intent(AboutUsActivity.this, ProfileManagementActivity.class); // Thay ProfileManagementActivity bằng tên activity của bạn
            startActivity(intent);
        });
    }
}
