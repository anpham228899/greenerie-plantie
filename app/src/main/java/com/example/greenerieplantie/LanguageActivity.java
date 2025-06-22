package com.example.greenerieplantie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class LanguageActivity extends AppCompatActivity {

    private TextView titleLanguage;
    private TextView textLanguageEngsub;
    private TextView titleLanguageVietsub;
    private SharedPreferences sharedPreferences;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        // Ẩn thanh ActionBar nếu có
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Khởi tạo các thành phần
        titleLanguage = findViewById(R.id.title_language);
        textLanguageEngsub = findViewById(R.id.text_language_engsub);
        titleLanguageVietsub = findViewById(R.id.title_language_vietsub);
        backButton = findViewById(R.id.imgbtn_language_back);

        // Lấy SharedPreferences để lưu ngôn ngữ đã chọn
        sharedPreferences = getSharedPreferences("LanguagePrefs", Context.MODE_PRIVATE);

        // Đặt ngôn ngữ theo cài đặt hiện tại
        setLanguage(getLanguagePreference());

        // Sự kiện quay lại về trang trước đó (không cần chuyển hướng về Homepage)
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Chỉ đóng màn hình LanguageActivity mà không chuyển về trang khác
            }
        });

        // Sự kiện chọn ngôn ngữ tiếng Anh
        textLanguageEngsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLanguage("en"); // Chuyển sang tiếng Anh
            }
        });

        // Sự kiện chọn ngôn ngữ tiếng Việt
        titleLanguageVietsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLanguage("vi"); // Chuyển sang tiếng Việt
            }
        });
    }

    // Cập nhật ngôn ngữ và màu chữ
    private void setLanguage(String languageCode) {
        // Lưu ngôn ngữ vào SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("language", languageCode);
        editor.apply();

        // Thay đổi ngôn ngữ ứng dụng
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Context context = getBaseContext();
        android.content.res.Configuration config = context.getResources().getConfiguration();
        config.setLocale(locale);

        // Áp dụng cấu hình mới mà không tái tạo lại Activity
        context.getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Cập nhật màu sắc chữ
        if (languageCode.equals("en")) {
            textLanguageEngsub.setTextColor(getResources().getColor(R.color.green_primary)); // Màu xanh khi chọn tiếng Anh
            titleLanguageVietsub.setTextColor(getResources().getColor(R.color.black)); // Màu đen cho tiếng Việt
        } else if (languageCode.equals("vi")) {
            textLanguageEngsub.setTextColor(getResources().getColor(R.color.black)); // Màu đen cho tiếng Anh
            titleLanguageVietsub.setTextColor(getResources().getColor(R.color.green_primary)); // Màu xanh khi chọn tiếng Việt
        }

        // Cập nhật giao diện sau khi thay đổi ngôn ngữ
        updateUIForNewLanguage();
    }

    // Cập nhật giao diện sau khi thay đổi ngôn ngữ
    private void updateUIForNewLanguage() {
        textLanguageEngsub.setText(getResources().getString(R.string.text_language_engsub));
        titleLanguageVietsub.setText(getResources().getString(R.string.title_language_vietsub));
    }

    // Lấy ngôn ngữ hiện tại từ SharedPreferences
    private String getLanguagePreference() {
        return sharedPreferences.getString("language", "en"); // Mặc định là tiếng Anh
    }
}




