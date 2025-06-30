package com.example.greenerieplantie;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class ProfileManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_management);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.plant_news), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        NavMenuActivity.setupNavMenu(bottomNav, this, R.id.nav_profile);
        bottomNav.setSelectedItemId(R.id.nav_profile);

        findViewById(R.id.layoutProfileSettings).setOnClickListener(v -> openProfileSettingsActivity());
        findViewById(R.id.layoutOrderHistory).setOnClickListener(v -> openOrderHistoryActivity());
        findViewById(R.id.layoutCareReminder).setOnClickListener(v -> openCareReminderActivity());
        findViewById(R.id.layoutNotifications).setOnClickListener(v -> openNotificationsActivity());
        findViewById(R.id.layoutChangeLanguage).setOnClickListener(v -> openChangeLanguageActivity());
        findViewById(R.id.layoutAboutUs).setOnClickListener(v -> openAboutUsActivity());
        findViewById(R.id.layoutPolicy).setOnClickListener(v -> openPolicyActivity());
        findViewById(R.id.layoutCustomerSupport).setOnClickListener(v -> openCustomerSupportActivity());
    }
        private void openProfileSettingsActivity() {
            Intent intent = new Intent(ProfileManagementActivity.this, ProfileSettingActivity.class);
            startActivity(intent);
        }

        private void openOrderHistoryActivity() {
            Intent intent = new Intent(ProfileManagementActivity.this, OrderHistoryActivity.class);
            startActivity(intent);
        }

        private void openCareReminderActivity() {
            Intent intent = new Intent(ProfileManagementActivity.this, CareReminderActivity.class);
            startActivity(intent);
        }

        private void openNotificationsActivity() {
            Intent intent = new Intent(ProfileManagementActivity.this, NotificationActivity.class);
            startActivity(intent);
        }

        private void openChangeLanguageActivity() {
            Intent intent = new Intent(ProfileManagementActivity.this, LanguageActivity.class);
            startActivity(intent);
        }

        private void openAboutUsActivity() {
            Intent intent = new Intent(ProfileManagementActivity.this, AboutUsActivity.class);
            startActivity(intent);
        }

        private void openPolicyActivity() {
            Intent intent = new Intent(ProfileManagementActivity.this, PoliciesActivity.class);
            startActivity(intent);
        }

        private void openCustomerSupportActivity() {
            Intent intent = new Intent(ProfileManagementActivity.this, CustomerSupportActivity.class);
            startActivity(intent);
        }
}
