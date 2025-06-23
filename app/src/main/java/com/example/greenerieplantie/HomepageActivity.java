package com.example.greenerieplantie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView; // Make sure this import is present

public class HomepageActivity extends AppCompatActivity {

    private TextView titleHomepageWelcome;
    private EditText searchEditText;
    private ImageView profileImage;
    private ImageButton searchButton;
    private SharedPreferences sharedPreferences;

    private Button btnForYou, btnNewArrivals, btnBestSeller;
    private ImageButton btnNotification;

    private LinearLayout product1Layout, product2Layout, product3Layout, product4Layout, product5Layout, product6Layout, product7Layout, product8Layout, product9Layout;

    private ImageButton imgProduct1, imgProduct2, imgProduct3, imgProduct4, imgProduct5, imgProduct6, imgProduct7, imgProduct8, imgProduct9;

    private Button btnViewMorePlantNews, btnViewMoreAboutUs;
    private ImageButton imgBlog1, imgBlog2, imgAboutUs1, imgAboutUs2;
    private TextView titleBlog1, titleBlog2, titleAboutUs1, titleAboutUs2;

    // Declare BottomNavigationView here as a member variable
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.white));
        }

        titleHomepageWelcome = findViewById(R.id.title_homepage_welcome1);
        searchEditText = findViewById(R.id.et_homepage_search);
        searchButton = findViewById(R.id.btn_homepage_search);
        // Khởi tạo các thành phần
        titleHomepageWelcome = findViewById(R.id.title_homepage_welcome1);
        profileImage = findViewById(R.id.img_homepage_avatar_custimer);
        // Lấy SharedPreferences để lưu trữ thông tin người dùng
        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        updateUserInfo();


        btnForYou = findViewById(R.id.btn_homepage_for_you);
        btnNewArrivals = findViewById(R.id.btn_homepage_new_arrivals);
        btnBestSeller = findViewById(R.id.btn_homepage_best_seller);
        btnNotification = findViewById(R.id.icbtn_homepage_notification);

        product1Layout = findViewById(R.id.layout_homepage_product1);
        product2Layout = findViewById(R.id.layout_homepage_product2);
        product3Layout = findViewById(R.id.layout_homepage_product3);
        product4Layout = findViewById(R.id.layout_homepage_product4);
        product5Layout = findViewById(R.id.layout_homepage_product5);
        product6Layout = findViewById(R.id.layout_homepage_product6);
        product7Layout = findViewById(R.id.layout_homepage_product7);
        product8Layout = findViewById(R.id.layout_homepage_product8);
        product9Layout = findViewById(R.id.layout_homepage_product9);

        imgProduct1 = findViewById(R.id.img_homepage_product1_for_you);
        imgProduct2 = findViewById(R.id.img_homepage_product2_for_you);
        imgProduct3 = findViewById(R.id.img_homepage_product3_for_you);
        imgProduct4 = findViewById(R.id.img_homepage_product4_for_you);
        imgProduct5 = findViewById(R.id.img_homepage_product5_for_you);
        imgProduct6 = findViewById(R.id.img_homepage_product6_for_you);
        imgProduct7 = findViewById(R.id.img_homepage_product7_for_you);
        imgProduct8 = findViewById(R.id.img_homepage_product8_for_you);
        imgProduct9 = findViewById(R.id.img_homepage_product9_for_you);

        imgProduct1.setOnClickListener(v -> openProductDetail("product1"));
        imgProduct2.setOnClickListener(v -> openProductDetail("product2"));
        imgProduct3.setOnClickListener(v -> openProductDetail("product3"));
        imgProduct4.setOnClickListener(v -> openProductDetail("product4"));
        imgProduct5.setOnClickListener(v -> openProductDetail("product5"));
        imgProduct6.setOnClickListener(v -> openProductDetail("product6"));
        imgProduct7.setOnClickListener(v -> openProductDetail("product7"));
        imgProduct8.setOnClickListener(v -> openProductDetail("product8"));
        imgProduct9.setOnClickListener(v -> openProductDetail("product9"));

        btnViewMorePlantNews = findViewById(R.id.btn_homepage_view_more_blog);
        btnViewMoreAboutUs = findViewById(R.id.btn_homepage_viewmore_abus);

        imgBlog1 = findViewById(R.id.img_homepage_blog1);
        imgBlog2 = findViewById(R.id.img_homepage_blog2);
        imgAboutUs1 = findViewById(R.id.img_homepage_about_us1);
        imgAboutUs2 = findViewById(R.id.img_homepage_abus2);

        titleBlog1 = findViewById(R.id.title_homepage_blog1_title);
        titleBlog2 = findViewById(R.id.title_homepage_blog2_title);
        titleAboutUs1 = findViewById(R.id.title_homepage_abus1);
        titleAboutUs2 = findViewById(R.id.title_homepage_abus2);

        btnViewMorePlantNews.setOnClickListener(v -> {
            Intent intent = new Intent(HomepageActivity.this, PlantNewsActivity.class);
            startActivity(intent);
        });

        btnViewMoreAboutUs.setOnClickListener(v -> {
            Intent intent = new Intent(HomepageActivity.this, AboutUsActivity.class);
            startActivity(intent);
        });

        imgBlog1.setOnClickListener(v -> openBlogDetail(1));
        imgBlog2.setOnClickListener(v -> openBlogDetail(2));
        titleBlog1.setOnClickListener(v -> openBlogDetail(1));
        titleBlog2.setOnClickListener(v -> openBlogDetail(2));

        imgAboutUs1.setOnClickListener(v -> openAboutUsDetail(1));
        imgAboutUs2.setOnClickListener(v -> openAboutUsDetail(2));
        titleAboutUs1.setOnClickListener(v -> openAboutUsDetail(1));
        titleAboutUs2.setOnClickListener(v -> openAboutUsDetail(2));


        searchButton.setOnClickListener(v -> {
            String query = searchEditText.getText().toString().trim();
            if (!query.isEmpty()) {
                searchProduct(query);
            } else {
                Toast.makeText(this, "Please enter a product to search!", Toast.LENGTH_SHORT).show();
            }
        });

        btnForYou.setOnClickListener(v -> updateTab("foryou"));
        btnNewArrivals.setOnClickListener(v -> updateTab("new"));
        btnBestSeller.setOnClickListener(v -> updateTab("best"));

        updateTab("foryou");

        btnNotification.setOnClickListener(v -> {
            Intent intent = new Intent(HomepageActivity.this, NotificationActivity.class);
            startActivity(intent);
        });

        // --- FIX STARTS HERE ---
        // Initialize bottomNavigationView BEFORE setting the listener on it
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                Toast.makeText(this, "Homepage", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.nav_product) {
                Toast.makeText(this, "Reminder", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, ProductActivity.class));
                return true;
            } else if (id == R.id.nav_chatbot) {
                Toast.makeText(this, "Chatbot", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, ChatbotActivity.class));
                return true;
            } else if (id == R.id.nav_cart) {
                Toast.makeText(this, "Cart", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, CartActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, ProfileManagementActivity.class));
                return true;
            }
            return false;
        });

        // This line below is redundant now that bottomNavigationView is initialized above.
        // It's also likely `NavMenuActivity.setupNavMenu` expects the same instance.
        // If `NavMenuActivity.setupNavMenu` also sets the listener, you might have conflicting listeners.
        // It's usually better to use only one place to set the listener.
        // If NavMenuActivity.setupNavMenu handles selection, you might remove the manual setOnItemSelectedListener above.
        // For now, I'll keep the initialization and the listener, and remove the redundant `bottomNav` declaration.
        NavMenuActivity.setupNavMenu(bottomNavigationView, this, R.id.nav_home);
        // --- FIX ENDS HERE ---
    }

    private void openProductDetail(String productId) {
        Intent intent = new Intent(HomepageActivity.this, ProductDetailActivity.class);
        intent.putExtra("product_id", productId);
        startActivity(intent);
    }

    private void openBlogDetail(int blogId) {
        Intent intent = new Intent(HomepageActivity.this, PlantNewsActivity.class);
        intent.putExtra("blog_id", blogId); // Pass blog ID to detail activity
        startActivity(intent);
    }

    private void openAboutUsDetail(int aboutUsId) {
        Intent intent = new Intent(HomepageActivity.this, AboutUsActivity.class);
        intent.putExtra("about_us_id", aboutUsId); // Pass About Us ID to detail activity
        startActivity(intent);
    }
    private void updateUserInfo() {
        String userName = sharedPreferences.getString("userName", null);
        String userImage = sharedPreferences.getString("userImage", null);

        if (userName == null || userName.isEmpty()) {
            userName = getResources().getString(R.string.default_user_name);
        }

        String welcomeMessage1 = getString(R.string.title_homepage_welcome1);
        // This line seems to be an issue or incomplete based on its usage in string.xml
        // If default_user_name_real takes a format argument, ensure it's correctly used.
        // String welcomeMessage2 = getString(R.string.default_user_name_real, userName);


        if (userImage == null || userImage.isEmpty()) {
            profileImage.setImageResource(R.mipmap.ic_launcher);
        } else {
            // Logic to load user image (e.g., using Glide or Picasso)
        }
    }


    private void updateTab(String selectedTab) {
        btnForYou.setTextColor(Color.parseColor("#BDBDBD"));
        btnNewArrivals.setTextColor(Color.parseColor("#BDBDBD"));
        btnBestSeller.setTextColor(Color.parseColor("#BDBDBD"));

        product1Layout.setVisibility(View.GONE);
        product2Layout.setVisibility(View.GONE);
        product3Layout.setVisibility(View.GONE);
        product4Layout.setVisibility(View.GONE);
        product5Layout.setVisibility(View.GONE);
        product6Layout.setVisibility(View.GONE);
        product7Layout.setVisibility(View.GONE);
        product8Layout.setVisibility(View.GONE);
        product9Layout.setVisibility(View.GONE);

        switch (selectedTab) {
            case "foryou":
                btnForYou.setTextColor(Color.parseColor("#517B2C"));
                product1Layout.setVisibility(View.VISIBLE);
                product2Layout.setVisibility(View.VISIBLE);
                product3Layout.setVisibility(View.VISIBLE);
                break;
            case "new":
                btnNewArrivals.setTextColor(Color.parseColor("#517B2C"));
                product4Layout.setVisibility(View.VISIBLE);
                product5Layout.setVisibility(View.VISIBLE);
                product8Layout.setVisibility(View.VISIBLE);
                break;
            case "best":
                btnBestSeller.setTextColor(Color.parseColor("#517B2C"));
                product6Layout.setVisibility(View.VISIBLE);
                product7Layout.setVisibility(View.VISIBLE);
                product9Layout.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void searchProduct(String query) {
        Toast.makeText(this, "Searching for: " + query, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
            Toast.makeText(this, "Chia sẻ", Toast.LENGTH_SHORT).show();
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Sharing Homepage Info");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out the homepage!");
            startActivity(Intent.createChooser(shareIntent, "Share via"));
            return true;
        } else if (id == R.id.action_home) {
            startActivity(new Intent(this, HomepageActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
