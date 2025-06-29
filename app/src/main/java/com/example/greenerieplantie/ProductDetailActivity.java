package com.example.greenerieplantie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapters.FeedbackAdapter;
import adapters.ProductImageSliderAdapter;
import connectors.CartConnector;
import models.Cart;
import models.Feedback;
import models.Product;
import utils.ListCart;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView productImageView;
    private ImageView backButton;
    private TextView productNameTextView;
    private TextView productCategoryTextView;
    private ImageView productCategoryIcon;
    private TextView productOriginalPriceTextView;
    private TextView productCurrentPriceTextView;
    private TextView productDiscountTextView;
    private TextView productDescriptionTextView;
    private TextView productLevelTextView;
    private TextView productWaterTextView;
    private TextView productSpecialConditionsTextView;
    private TextView productGrowthPeriodTextView;
    private TextView productSellingAmountTextView;
    private LinearLayout discountBubbleLayout;
    private ViewPager2 imageSlider;

    private RecyclerView rvFeedback;
    private FeedbackAdapter feedbackAdapter;
    private List<Feedback> fullFeedbackList;
    private TextView tvViewAllFeedback;
    private boolean isFeedbackExpanded = false;
    private final int INITIAL_FEEDBACK_LIMIT = 3;

    private Button btnAddToCart;
    private Button btnBuyNow;

    private Product currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        imageSlider = findViewById(R.id.product_image_slider);

        // Ánh xạ view
//        productImageView = findViewById(R.id.product_detail_image);
        backButton = findViewById(R.id.product_detail_back_button);
        productNameTextView = findViewById(R.id.product_detail_name);
        productCategoryTextView = findViewById(R.id.product_detail_category);
        productCategoryIcon = findViewById(R.id.product_category_icon);
        productOriginalPriceTextView = findViewById(R.id.product_detail_original_price);
        productCurrentPriceTextView = findViewById(R.id.product_detail_current_price);
        productDiscountTextView = findViewById(R.id.product_detail_discount_percentage);
        productDescriptionTextView = findViewById(R.id.product_detail_description);
        productLevelTextView = findViewById(R.id.product_detail_level);
        productWaterTextView = findViewById(R.id.product_detail_water);
        productSpecialConditionsTextView = findViewById(R.id.product_detail_special_conditions);
        productGrowthPeriodTextView = findViewById(R.id.product_detail_growth_period);
        productSellingAmountTextView = findViewById(R.id.product_detail_selling_amount);
        discountBubbleLayout = findViewById(R.id.product_discount_bubble_layout);
        btnAddToCart = findViewById(R.id.btn_add_to_cart);
        btnBuyNow = findViewById(R.id.btn_buy_now);

        rvFeedback = findViewById(R.id.rvFeedback);
        tvViewAllFeedback = findViewById(R.id.tv_view_all_feedback);
        fullFeedbackList = new ArrayList<>();
        loadAllFeedbackData();

        rvFeedback.setLayoutManager(new LinearLayoutManager(this));
        feedbackAdapter = new FeedbackAdapter(fullFeedbackList, INITIAL_FEEDBACK_LIMIT);
        rvFeedback.setAdapter(feedbackAdapter);

        tvViewAllFeedback.setVisibility(fullFeedbackList.size() > INITIAL_FEEDBACK_LIMIT ? View.VISIBLE : View.GONE);
        tvViewAllFeedback.setOnClickListener(v -> toggleFeedbackView());

        backButton.setOnClickListener(v -> finish());

        // --- Lấy product_id từ Intent và load từ Firebase ---
        String productId = getIntent().getStringExtra("product_id");

        if (productId == null || productId.isEmpty()) {
            Toast.makeText(this, "No product data provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        FirebaseDatabase.getInstance().getReference("products").child(productId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult().exists()) {

                        try {
                            DataSnapshot snapshot = task.getResult();

                            String name = String.valueOf(snapshot.child("product_name").getValue());
                            String category = String.valueOf(snapshot.child("category_id").getValue());
                            String description = String.valueOf(snapshot.child("product_description").getValue());
                            String instruction = String.valueOf(snapshot.child("product_instruction").getValue());
                            String level = String.valueOf(snapshot.child("product_level").getValue());
                            String water = String.valueOf(snapshot.child("water_demand").getValue());
                            String cond = String.valueOf(snapshot.child("conditions").getValue());
                            String rating = String.valueOf(snapshot.child("product_rating").getValue());
                            String name_vi = String.valueOf(snapshot.child("product_name_vi").getValue());
                            String description_vi = String.valueOf(snapshot.child("product_description_vi").getValue());
                            String instruction_vi = String.valueOf(snapshot.child("product_instruction_vi").getValue());
                            String level_vi = String.valueOf(snapshot.child("product_level_vi").getValue());
                            String water_vi = String.valueOf(snapshot.child("water_demand_vi").getValue());
                            String cond_vi = String.valueOf(snapshot.child("conditions_vi").getValue());
                            double price = 0;
                            try {
                                Object rawPrice = snapshot.child("product_price").getValue();
                                price = rawPrice instanceof Number
                                        ? ((Number) rawPrice).doubleValue()
                                        : Double.parseDouble(String.valueOf(rawPrice));
                            } catch (Exception e) {}

                            double prevPrice = 0;
                            try {
                                Object rawPrev = snapshot.child("product_previous_price").getValue();
                                prevPrice = rawPrev instanceof Number
                                        ? ((Number) rawPrev).doubleValue()
                                        : Double.parseDouble(String.valueOf(rawPrev));
                            } catch (Exception e) {}

                            int discount = 0;
                            try {
                                Object rawDiscount = snapshot.child("product_discount").getValue();
                                discount = rawDiscount instanceof Number
                                        ? ((Number) rawDiscount).intValue()
                                        : Integer.parseInt(String.valueOf(rawDiscount));
                            } catch (Exception e) {}

                            int stock = 0;
                            try {
                                Object rawStock = snapshot.child("product_stock").getValue();
                                stock = rawStock instanceof Number
                                        ? ((Number) rawStock).intValue()
                                        : Integer.parseInt(String.valueOf(rawStock));
                            } catch (Exception e) {}

                            // Parse images
                            String image1 = null;
                            if (snapshot.child("product_images").child("image1").exists()) {
                                image1 = String.valueOf(snapshot.child("product_images").child("image1").getValue());
                            }

                            // Khởi tạo product đơn giản (nếu bạn có constructor phù hợp)
                            currentProduct = new Product();
                            currentProduct.setProduct_name(name);
                            currentProduct.setCategory_id(category);
                            currentProduct.setProduct_description(description);
                            currentProduct.setProduct_instruction(instruction);
                            currentProduct.setProduct_level(level);
                            currentProduct.setWater_demand(water);
                            currentProduct.setConditions(cond);
                            currentProduct.setProduct_price(price);
                            currentProduct.setProduct_previous_price(prevPrice);
                            currentProduct.setProduct_discount(discount);
                            currentProduct.setProduct_stock(stock);
                            currentProduct.setProduct_name_vi(name_vi);
                            currentProduct.setProduct_description_vi(description_vi);
                            currentProduct.setProduct_instruction_vi(instruction_vi);
                            currentProduct.setProduct_level_vi(level_vi);
                            currentProduct.setWater_demand_vi(water_vi);
                            currentProduct.setConditions_vi(cond_vi);
                            Map<String, String> images = new HashMap<>();
                            DataSnapshot imageSnap = snapshot.child("product_images");

                            for (DataSnapshot imgEntry : imageSnap.getChildren()) {
                                String key = imgEntry.getKey();
                                Object val = imgEntry.getValue();

                                if (key != null && val != null) {
                                    images.put(key, String.valueOf(val));
                                }
                            }
                            currentProduct.setProduct_images(images);
                            currentProduct.setProduct_id(productId);
                            showProductDetail(currentProduct);
                            Log.d("AddToCart", "product_id = " + currentProduct.getProduct_id());
                        } catch (Exception e) {
                            Log.e("ProductDetail", "Parse error: " + e.getMessage());
                            Toast.makeText(this, "Product data error", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        Toast.makeText(this, "Failed to load product", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private void setupImageSlider(Product product) {
        if (product.getProduct_images() != null && !product.getProduct_images().isEmpty()) {
            List<String> imageList = new ArrayList<>(product.getProduct_images().values());
            ProductImageSliderAdapter sliderAdapter = new ProductImageSliderAdapter(imageList);
            imageSlider.setAdapter(sliderAdapter);
        } else {
            productImageView.setImageResource(R.mipmap.ic_launcher);
        }
    }
    private void displayPricing(Product product) {
        productCurrentPriceTextView.setText(String.format("%s %,.0f", getString(R.string.currency_unit_vnd), product.getProduct_price()));

        if (product.getProduct_discount() > 0) {
            productOriginalPriceTextView.setText(String.format("%s %,.0f", getString(R.string.currency_unit_vnd_bold), product.getProduct_previous_price()));
            productOriginalPriceTextView.setPaintFlags(productOriginalPriceTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            productOriginalPriceTextView.setVisibility(View.VISIBLE);

            productDiscountTextView.setText(product.getProduct_discount() + "%");
            discountBubbleLayout.setVisibility(View.VISIBLE);
        } else {
            productOriginalPriceTextView.setVisibility(View.GONE);
            discountBubbleLayout.setVisibility(View.GONE);
        }
    }
    private void displayLocalizedInfo(Product product) {
        productNameTextView.setText(product.getLocalizedProductName(this));
        productDescriptionTextView.setText(product.getLocalizedProductDescription(this));
        productLevelTextView.setText(product.getLocalizedProductLevel(this));
        productWaterTextView.setText(product.getLocalizedWaterDemand(this));
        productSpecialConditionsTextView.setText(product.getLocalizedConditions(this));
        productGrowthPeriodTextView.setText(product.getLocalizedProductInstruction(this));
        productSellingAmountTextView.setText(product.getProduct_stock() + " in stock");
    }
    private void setupActionButtons() {
        btnAddToCart.setOnClickListener(v -> handleAddToCart());
        btnBuyNow.setOnClickListener(v -> handleBuyNow());
    }
    private void showProductDetail(Product product) {
        setupImageSlider(product);
        productCategoryTextView.setText(product.getCategory_id());
        setCategoryIcon(product.getCategory_id());

        displayPricing(product);
        displayLocalizedInfo(product);
        setupActionButtons();
    }

//
//    private void showProductDetail(Product product) {
//        if (product.getProduct_images() != null && !product.getProduct_images().isEmpty()) {
//            List<String> imageList = new ArrayList<>(product.getProduct_images().values());
//            ProductImageSliderAdapter sliderAdapter = new ProductImageSliderAdapter(imageList);
//            imageSlider.setAdapter(sliderAdapter);
//        } else {
//            productImageView.setImageResource(R.mipmap.ic_launcher);
//        }
//
//        productNameTextView.setText(product.getProduct_name());
//        productCategoryTextView.setText(product.getCategory_id());
//        setCategoryIcon(product.getCategory_id());
//
//        productCurrentPriceTextView.setText( String.format("%s %,.0f", getString( R.string.currency_unit_vnd), product.getProduct_price()));
//
//        if (product.getProduct_discount() > 0) {
//            productOriginalPriceTextView.setText(  String.format("%s %,.0f", getString(R.string.currency_unit_vnd_bold), product.getProduct_previous_price()));
//            productOriginalPriceTextView.setPaintFlags(productOriginalPriceTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//            productOriginalPriceTextView.setVisibility(View.VISIBLE);
//
//            productDiscountTextView.setText(product.getProduct_discount() + "%");
//            discountBubbleLayout.setVisibility(View.VISIBLE);
//        } else {
//            productOriginalPriceTextView.setVisibility(View.GONE);
//            discountBubbleLayout.setVisibility(View.GONE);
//        }
//
//        productDescriptionTextView.setText(product.getProduct_description());
//        productLevelTextView.setText(product.getProduct_level());
//        productWaterTextView.setText(product.getWater_demand());
//        productSpecialConditionsTextView.setText(product.getConditions());
//        productGrowthPeriodTextView.setText(product.getProduct_instruction());
//        productSellingAmountTextView.setText(product.getProduct_stock() + " in stock");
//
//        btnAddToCart.setOnClickListener(v -> handleAddToCart());
//        btnBuyNow.setOnClickListener(v -> handleBuyNow());
//    }

    private void handleAddToCart() {
        SharedPreferences prefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String userId = prefs.getString("user_uid", null);
        if (userId == null) {
            Toast.makeText(this, "Bạn chưa đăng nhập!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentProduct != null) {
            Cart cartItem = new Cart();
            cartItem.setProduct_id(currentProduct.getProduct_id());
            cartItem.setProduct_name(currentProduct.getProduct_name());
            cartItem.setProduct_price((int) currentProduct.getProduct_price());
            cartItem.setQuantity(1);
            cartItem.setSelected(true);


            cartItem.setProduct_images(currentProduct.getProduct_images());

            CartConnector connector = new CartConnector(userId);
            connector.addToCart(cartItem, success -> {
                if (success) {
                    Snackbar.make(findViewById(android.R.id.content),
                                    currentProduct.getProduct_name() + " đã thêm vào giỏ hàng!",
                                    Snackbar.LENGTH_SHORT)
                            .setAction("XEM GIỎ", view -> {
                                startActivity(new Intent(ProductDetailActivity.this, CartActivity.class));
                            }).show();
                } else {
                    Toast.makeText(this, "Thêm giỏ hàng thất bại!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void handleBuyNow() {
        if (currentProduct != null) {
            Intent intent = new Intent(ProductDetailActivity.this, PaymentActivity.class);
            intent.putExtra("product_id", currentProduct.getProduct_id()); // ✅ gửi product_id
            intent.putExtra("buy_now", true); // ✅ gửi cờ mua ngay
            startActivity(intent);
        } else {
            Toast.makeText(this, "Cannot buy: Product data is missing.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setCategoryIcon(String category) {
        int iconResId;
        switch (category) {
            case "Food Crops":
                iconResId = R.mipmap.ic_food_crops;
                break;
            case "Veggies":
                iconResId = R.mipmap.ic_veggies;
                break;
            case "Fruit Trees":
                iconResId = R.mipmap.ic_label_fruit_trees;
                break;
            default:
                iconResId = R.mipmap.ic_launcher;
                break;
        }
        productCategoryIcon.setImageResource(iconResId);
    }

    private void loadAllFeedbackData() {
        fullFeedbackList.clear();

        fullFeedbackList.add(new Feedback(
                R.mipmap.ic_launcher,
                "Nguyễn Văn A", 5,
                "Giao hàng nhanh chóng và sản phẩm đúng mô tả. Cây rất khỏe mạnh, rễ phát triển tốt. Tôi rất hài lòng với sản phẩm này.",
                "20/06/2025"
        ));
        fullFeedbackList.add(new Feedback(
                R.mipmap.ic_launcher,
                "Trần Thị B", 4,
                "Cây tươi tốt, đóng gói cẩn thận. Tuy nhiên, thời gian giao hàng hơi lâu một chút so với dự kiến. Hy vọng lần sau sẽ nhanh hơn.",
                "18/06/2025"
        ));
        fullFeedbackList.add(new Feedback(
                R.mipmap.ic_launcher,
                "Lê Cảnh C", 5,
                "Sản phẩm tuyệt vời! Cây con khỏe mạnh, dễ trồng và đã cho ra quả sau vài tháng. Sẽ giới thiệu cho bạn bè.",
                "15/06/2025"
        ));
        fullFeedbackList.add(new Feedback(
                R.mipmap.ic_launcher,
                "Phạm Doãn D", 3,
                "Cây đẹp nhưng vận chuyển làm rụng mất vài lá. Mong shop cải thiện khâu đóng gói.",
                "14/06/2025"
        ));
        fullFeedbackList.add(new Feedback(
                R.mipmap.ic_launcher,
                "Vũ Thị E", 5,
                "Mầm cây nảy nở rất nhanh, chất lượng đất tốt. Đáng tiền!",
                "12/06/2025"
        ));
        fullFeedbackList.add(new Feedback(
                R.mipmap.ic_launcher,
                "Hoàng Đức F", 4,
                "Cây sống khỏe. Tư vấn nhiệt tình. Sẽ ủng hộ tiếp.",
                "10/06/2025"
        ));
        fullFeedbackList.add(new Feedback(
                R.mipmap.ic_launcher,
                "Mai Lan G", 5,
                "Hàng chất lượng, giao đúng hẹn. Rất hài lòng.",
                "08/06/2025"
        ));
    }

    private void toggleFeedbackView() {
        if (isFeedbackExpanded) {
            feedbackAdapter.setDisplayLimit(INITIAL_FEEDBACK_LIMIT);
            tvViewAllFeedback.setText(getString(R.string.view_all_feedback));
            isFeedbackExpanded = false;
        } else {
            feedbackAdapter.setDisplayLimit(fullFeedbackList.size());
            tvViewAllFeedback.setText(getString(R.string.show_less_feedback));
            isFeedbackExpanded = true;
        }
    }
}
