package com.example.greenerieplantie;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import adapters.ListNotificationsAdapter;
import utils.ListNotifications;
import models.Notifications;

public class ListNotificationsActivity extends AppCompatActivity {

    private RecyclerView notificationsRecyclerView;
    private ListNotificationsAdapter adapter;
    private List<Notifications> listnotification;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notifications);

        // Khởi tạo RecyclerView
        notificationsRecyclerView = findViewById(R.id.newsRecyclerView);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Lấy SharedPreferences để kiểm tra trạng thái của "In-app notifications"
        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        boolean isInAppNotificationEnabled = sharedPreferences.getBoolean("inAppNotification", false);

        if (isInAppNotificationEnabled) {
            // Nếu "In-app notifications" bật, lấy danh sách thông báo
            loadNotificationsData();
        } else {
            // Nếu tắt, ẩn RecyclerView và thông báo cho người dùng
            notificationsRecyclerView.setVisibility(View.GONE);
            Toast.makeText(this, "In-app notifications are turned off.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadNotificationsData() {
        // Lấy danh sách thông báo
        listnotification = ListNotifications.getNotificationList(); // Lấy danh sách từ ListNotifications
        // Gán adapter cho RecyclerView
        adapter = new ListNotificationsAdapter(listnotification);
        notificationsRecyclerView.setAdapter(adapter);
    }
}
