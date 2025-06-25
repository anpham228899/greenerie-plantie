package com.example.greenerieplantie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import models.Product;
import models.NewsDetail;
import utils.ListProduct;
import utils.ListNewsDetail;

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

    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.white));
        }

        titleHomepageWelcome = findViewById(R.id.title_homepage_welcome1);
        searchEditText = findViewById(R.id.et_product_search);
        searchButton = findViewById(R.id.btn_product_search);
        profileImage = findViewById(R.id.img_homepage_avatar_custimer);
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

        productList = ListProduct.getSampleProductData();

        imgProduct1.setOnClickListener(v -> openProductDetail("P001"));
        imgProduct2.setOnClickListener(v -> openProductDetail("P002"));
        imgProduct3.setOnClickListener(v -> openProductDetail("P003"));
        imgProduct4.setOnClickListener(v -> openProductDetail("P004"));
        imgProduct5.setOnClickListener(v -> openProductDetail("P005"));
        imgProduct6.setOnClickListener(v -> openProductDetail("P006"));
        imgProduct7.setOnClickListener(v -> openProductDetail("P007"));
        imgProduct8.setOnClickListener(v -> openProductDetail("P008"));
        imgProduct9.setOnClickListener(v -> openProductDetail("P009"));

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

        // Lấy dữ liệu blog từ ListNewsDetail
        List<NewsDetail> newsList = ListNewsDetail.getSampleNews();
        if (newsList.size() >= 2) {
            NewsDetail news1 = newsList.get(0);
            NewsDetail news2 = newsList.get(1);

            imgBlog1.setImageResource(news1.getImageResId());
            titleBlog1.setText(news1.getTitle());
            imgBlog2.setImageResource(news2.getImageResId());
            titleBlog2.setText(news2.getTitle());

            imgBlog1.setOnClickListener(v -> openBlogDetail(news1));
            titleBlog1.setOnClickListener(v -> openBlogDetail(news1));
            imgBlog2.setOnClickListener(v -> openBlogDetail(news2));
            titleBlog2.setOnClickListener(v -> openBlogDetail(news2));
        }

        imgAboutUs1.setOnClickListener(v -> openAboutUsDetail(1));
        imgAboutUs2.setOnClickListener(v -> openAboutUsDetail(2));
        titleAboutUs1.setOnClickListener(v -> openAboutUsDetail(1));
        titleAboutUs2.setOnClickListener(v -> openAboutUsDetail(2));

        searchButton.setOnClickListener(v -> {
            String query = searchEditText.getText().toString().trim();
            if (!query.isEmpty()) {
                Intent intent = new Intent(HomepageActivity.this, ProductActivity.class);
                intent.putExtra("search_query", query);
                startActivity(intent);
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

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        NavMenuActivity.setupNavMenu(bottomNav, this, R.id.nav_home);
        bottomNav.setSelectedItemId(R.id.nav_home);
    }

    private void openProductDetail(String productId) {
        for (Product product : productList) {
            if (product.getProductId().equals(productId)) {
                Intent intent = new Intent(HomepageActivity.this, ProductDetailActivity.class);
                intent.putExtra("product_data", product);
                startActivity(intent);
                return;
            }
        }
        Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show();
    }

    // Đã thay thế openBlogDetail(int) bằng openBlogDetail(NewsDetail)
    private void openBlogDetail(NewsDetail newsDetail) {
        Intent intent = new Intent(HomepageActivity.this, NewsDetailActivity.class);
        intent.putExtra("newsDetail", newsDetail); // key trùng với NewsDetailActivity
        startActivity(intent);
    }

    private void openAboutUsDetail(int aboutUsId) {
        Intent intent = new Intent(HomepageActivity.this, AboutUsActivity.class);
        intent.putExtra("about_us_id", aboutUsId);
        startActivity(intent);
    }

    private void updateUserInfo() {
        String userName = sharedPreferences.getString("userName", null);
        String userImage = sharedPreferences.getString("userImage", null);

        if (userName == null || userName.isEmpty()) {
            userName = getResources().getString(R.string.default_user_name);
        }

        if (userImage == null || userImage.isEmpty()) {
            profileImage.setImageResource(R.mipmap.ic_launcher);
        }
        // Có thể dùng Glide/Picasso để load ảnh từ URL nếu cần
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
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
