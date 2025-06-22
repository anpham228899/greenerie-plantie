package com.example.greenerieplantie;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavMenuActivity {

    public static void setupNavMenu(BottomNavigationView bottomNavigationView, Context context, int selectedItemId) {
        bottomNavigationView.setSelectedItemId(selectedItemId); // Chọn item mặc định
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home && !(context instanceof HomepageActivity)) {
                context.startActivity(new Intent(context, HomepageActivity.class));
                return true;
            } else if (id == R.id.nav_product && !(context instanceof ProductActivity)) {
                context.startActivity(new Intent(context, ProductActivity.class));
                return true;
            } else if (id == R.id.nav_chatbot && !(context instanceof ChatbotActivity)) {
                context.startActivity(new Intent(context, ChatbotActivity.class));
                return true;
            } else if (id == R.id.nav_cart && !(context instanceof CartActivity)) {
                context.startActivity(new Intent(context, CartActivity.class));
                return true;
            } else if (id == R.id.nav_profile && !(context instanceof ProfileManagementActivity)) {
                context.startActivity(new Intent(context, ProfileManagementActivity.class));
                return true;
            }

            return false;
        });
    }
}


