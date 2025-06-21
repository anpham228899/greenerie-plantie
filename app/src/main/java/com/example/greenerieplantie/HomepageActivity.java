package com.example.greenerieplantie;

import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomepageActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private TextView titleHomepageWelcome;
    private EditText searchEditText;
    private ImageButton searchButton;

    private Button btnForYou, btnNewArrivals, btnBestSeller;
    private ImageButton btnNotification;

    private LinearLayout product1Layout, product2Layout, product3Layout, product4Layout, product5Layout, product6Layout, product7Layout, product8Layout, product9Layout;

    // NEW: ImageButtons for product images
    private ImageButton imgProduct1, imgProduct2, imgProduct3, imgProduct4, imgProduct5, imgProduct6, imgProduct7, imgProduct8, imgProduct9;

    // Declare variables for the new elements
    private Button btnViewMorePlantNews, btnViewMoreAboutUs;
    private ImageButton imgBlog1, imgBlog2, imgAboutUs1, imgAboutUs2;
    private TextView titleBlog1, titleBlog2, titleAboutUs1, titleAboutUs2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.white));
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        titleHomepageWelcome = findViewById(R.id.title_homepage_welcome);
        searchEditText = findViewById(R.id.et_homepage_search);
        searchButton = findViewById(R.id.btn_homepage_search);

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

        // NEW: Mapping product image buttons
        imgProduct1 = findViewById(R.id.img_homepage_product1_for_you);
        imgProduct2 = findViewById(R.id.img_homepage_product2_for_you);
        imgProduct3 = findViewById(R.id.img_homepage_product3_for_you);
        imgProduct4 = findViewById(R.id.img_homepage_product4_for_you);
        imgProduct5 = findViewById(R.id.img_homepage_product5_for_you);
        imgProduct6 = findViewById(R.id.img_homepage_product6_for_you);
        imgProduct7 = findViewById(R.id.img_homepage_product7_for_you);
        imgProduct8 = findViewById(R.id.img_homepage_product8_for_you);
        imgProduct9 = findViewById(R.id.img_homepage_product9_for_you);

        // NEW: Set click listeners to open product detail
        imgProduct1.setOnClickListener(v -> openProductDetail("product1"));
        imgProduct2.setOnClickListener(v -> openProductDetail("product2"));
        imgProduct3.setOnClickListener(v -> openProductDetail("product3"));
        imgProduct4.setOnClickListener(v -> openProductDetail("product4"));
        imgProduct5.setOnClickListener(v -> openProductDetail("product5"));
        imgProduct6.setOnClickListener(v -> openProductDetail("product6"));
        imgProduct7.setOnClickListener(v -> openProductDetail("product7"));
        imgProduct8.setOnClickListener(v -> openProductDetail("product8"));
        imgProduct9.setOnClickListener(v -> openProductDetail("product9"));

        // Initialize views for "Plant News" and "About Us"
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

        // Set up "View More" buttons for navigation
        btnViewMorePlantNews.setOnClickListener(v -> {
            Intent intent = new Intent(HomepageActivity.this, PlantNewsActivity.class); // Target Plant News Activity
            startActivity(intent);
        });

        btnViewMoreAboutUs.setOnClickListener(v -> {
            Intent intent = new Intent(HomepageActivity.this, AboutUsActivity.class); // Target About Us Activity
            startActivity(intent);
        });

        // Set up ImageButton listeners for each blog
        imgBlog1.setOnClickListener(v -> openBlogDetail(1)); // Open Blog 1 details
        imgBlog2.setOnClickListener(v -> openBlogDetail(2)); // Open Blog 2 details
        titleBlog1.setOnClickListener(v -> openBlogDetail(1)); // Open Blog 1 details when title is clicked
        titleBlog2.setOnClickListener(v -> openBlogDetail(2)); // Open Blog 2 details when title is clicked

        // Set up listeners for "About Us" sections
        imgAboutUs1.setOnClickListener(v -> openAboutUsDetail(1)); // Open About Us 1 details
        imgAboutUs2.setOnClickListener(v -> openAboutUsDetail(2)); // Open About Us 2 details
        titleAboutUs1.setOnClickListener(v -> openAboutUsDetail(1)); // Open About Us 1 details when title is clicked
        titleAboutUs2.setOnClickListener(v -> openAboutUsDetail(2)); // Open About Us 2 details when title is clicked

        // Existing code to handle other UI components
        String text = "Welcome to Greenery, An Pham!";
        SpannableString spannableString = new SpannableString(text);
        int start = text.indexOf("Greenery");
        int end = start + "Greenery".length();
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#517B2C")), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        titleHomepageWelcome.setText(spannableString);

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


        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                Toast.makeText(this, "Homepage", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, HomepageActivity.class));
                return true;
            } else if (id == R.id.nav_reminder) {
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
    }

    // Function to open Product detail page
    private void openProductDetail(String productId) {
        Intent intent = new Intent(HomepageActivity.this, ProductDetailActivity.class);
        intent.putExtra("product_id", productId);  // Pass product ID to the detail activity
        startActivity(intent);
    }

    // Function to open Blog detail page
    private void openBlogDetail(int blogId) {
        Intent intent = new Intent(HomepageActivity.this, PlantNewsActivity.class);
        intent.putExtra("blog_id", blogId); // Pass blog ID to detail activity
        startActivity(intent);
    }

    // Function to open About Us detail page
    private void openAboutUsDetail(int aboutUsId) {
        Intent intent = new Intent(HomepageActivity.this, AboutUsActivity.class);
        intent.putExtra("about_us_id", aboutUsId); // Pass About Us ID to detail activity
        startActivity(intent);
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






