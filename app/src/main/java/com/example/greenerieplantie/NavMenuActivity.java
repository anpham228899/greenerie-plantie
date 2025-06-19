package com.example.greenerieplantie;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavMenuActivity {

    public static void setupNavMenu(BottomNavigationView bottomNavigationView, Context context) {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                Toast.makeText(context, "Homepage", Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, HomepageActivity.class));
                return true;
            } else if (id == R.id.nav_reminder) {
                Toast.makeText(context, "Reminder", Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, CareReminderActivity.class));
                return true;
            } else if (id == R.id.nav_chatbot) {
                Toast.makeText(context, "Chatbot", Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, ChatbotActivity.class));
                return true;
            } else if (id == R.id.nav_cart) {
                Toast.makeText(context, "Cart", Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, CartActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
                Toast.makeText(context, "Profile", Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, ProfileManagementActivity.class));
                return true;
            } else {
                return false;
            }
        });
    }
}

