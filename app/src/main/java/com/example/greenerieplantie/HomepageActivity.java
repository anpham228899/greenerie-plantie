package com.example.greenerieplantie;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomepageActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.white)); // Đổi màu nền ActionBar thành trắng
        }


        bottomNavigationView = findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(android.view.MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    Toast.makeText(HomepageActivity.this, "", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.nav_reminder) {
                    Toast.makeText(HomepageActivity.this, "", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.nav_chatbot) {
                    Toast.makeText(HomepageActivity.this, "", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.nav_cart) {
                    Toast.makeText(HomepageActivity.this, "", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.nav_profile) {
                    Toast.makeText(HomepageActivity.this, "", Toast.LENGTH_SHORT).show();
                    return true;
                }

                return false;
            }
        });
    }

    // Inflating menu vào Activity (hiển thị dấu ba chấm)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_detail, menu); // Inflate menu_news_detail.xml
        return true;
    }

    // Xử lý sự kiện khi người dùng chọn menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
            // Chia sẻ thông tin từ trang homepage (giả sử đây là hành động chia sẻ)
            Toast.makeText(this, "Chia sẻ", Toast.LENGTH_SHORT).show();
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Sharing Homepage Info");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out the homepage!");
            startActivity(Intent.createChooser(shareIntent, "Share via"));
            return true;
        } else if (id == R.id.action_home) {
            // Quay lại trang chủ
            startActivity(new Intent(this, HomepageActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


