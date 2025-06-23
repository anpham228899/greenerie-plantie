package com.example.greenerieplantie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NotificationActivity extends AppCompatActivity {

    private Switch inAppNotificationSwitch;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification);

        inAppNotificationSwitch = findViewById(R.id.switchInApp);

        // Lấy SharedPreferences để lưu trữ trạng thái của switch
        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        boolean isInAppNotificationEnabled = sharedPreferences.getBoolean("inAppNotification", false);
        inAppNotificationSwitch.setChecked(isInAppNotificationEnabled);

        // Set listener cho switch
        inAppNotificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Lưu trạng thái vào SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("inAppNotification", isChecked);
            editor.apply();

            if (isChecked) {
                // Load dữ liệu khi bật In-app notifications
                loadNotificationsData();
            } else {
                // Clear dữ liệu khi tắt In-app notifications
                clearNotificationsData();
            }
        });

        // Set listener cho nút back
        findViewById(R.id.btnBack).setOnClickListener(v -> {
            // Mở ListNotificationsActivity khi nhấn nút back
            Intent intent = new Intent(NotificationActivity.this, ListNotificationsActivity.class);
            startActivity(intent);
            finish();  // Đóng NotificationActivity
        });

        // For window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loadNotificationsData() {
        Toast.makeText(this, "Loading Notifications...", Toast.LENGTH_SHORT).show();
    }

    private void clearNotificationsData() {
        Toast.makeText(this, "Notifications turned off.", Toast.LENGTH_SHORT).show();
    }
}
