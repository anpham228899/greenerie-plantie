package com.example.greenerieplantie;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import models.Product;
import models.Feedback;
import adapters.FeedbackAdapter;
import utils.ListCart;

import java.util.ArrayList;
import java.util.List;

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

        productImageView = findViewById(R.id.product_detail_image);
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

        rvFeedback = findViewById(R.id.rvFeedback);
        tvViewAllFeedback = findViewById(R.id.tv_view_all_feedback);
        fullFeedbackList = new ArrayList<>();

        loadAllFeedbackData();

        rvFeedback.setLayoutManager(new LinearLayoutManager(this));
        feedbackAdapter = new FeedbackAdapter(fullFeedbackList, INITIAL_FEEDBACK_LIMIT);
        rvFeedback.setAdapter(feedbackAdapter);

        if (fullFeedbackList.size() > INITIAL_FEEDBACK_LIMIT) {
            tvViewAllFeedback.setVisibility(View.VISIBLE);
            tvViewAllFeedback.setText(getString(R.string.view_all_feedback));
        } else {
            tvViewAllFeedback.setVisibility(View.GONE);
        }
        tvViewAllFeedback.setOnClickListener(v -> toggleFeedbackView());

        backButton.setOnClickListener(v -> finish());

        btnAddToCart = findViewById(R.id.btn_add_to_cart);
        btnBuyNow = findViewById(R.id.btn_buy_now);

        if (getIntent().hasExtra("product_data")) {
            currentProduct = getIntent().getParcelableExtra("product_data");
            if (currentProduct != null) {
                productImageView.setImageResource(currentProduct.getImageResId());

                productNameTextView.setText(currentProduct.getName());

                productCategoryTextView.setText(currentProduct.getCategory());
                setCategoryIcon(currentProduct.getCategory());

                productCurrentPriceTextView.setText(String.format("%s %s", getString(R.string.currency_unit_vnd), currentProduct.getFormattedPrice()));

                if (currentProduct.getDiscountPercentage() > 0) {
                    productOriginalPriceTextView.setText(String.format("%s %s", getString(R.string.currency_unit_vnd), currentProduct.getFormattedOriginalPrice()));

                    productOriginalPriceTextView.setPaintFlags(productOriginalPriceTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    productOriginalPriceTextView.setVisibility(View.VISIBLE);

                    productDiscountTextView.setText(currentProduct.getFormattedDiscountPercentage());
                    discountBubbleLayout.setVisibility(View.VISIBLE);
                } else {
                    productOriginalPriceTextView.setVisibility(View.GONE);
                    discountBubbleLayout.setVisibility(View.GONE);
                }

                productDescriptionTextView.setText(currentProduct.getDescription());
                productLevelTextView.setText(currentProduct.getLevel());
                productWaterTextView.setText(currentProduct.getWaterNeeds());
                productSpecialConditionsTextView.setText(currentProduct.getSpecialConditions());
                productGrowthPeriodTextView.setText(currentProduct.getGrowthPeriod());
                productSellingAmountTextView.setText(currentProduct.getSellingAmount());

                btnAddToCart.setOnClickListener(v -> handleAddToCart());
                btnBuyNow.setOnClickListener(v -> handleBuyNow());

            } else {
                Toast.makeText(this, "Error: Product data not found.", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Error: No product data provided.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void handleAddToCart() {
        if (currentProduct != null) {
            ListCart.addOrUpdateProduct(currentProduct, 1);

            Snackbar.make(findViewById(android.R.id.content),
                            currentProduct.getName() + " added to cart!",
                            Snackbar.LENGTH_SHORT)
                    .setAction("VIEW CART", view -> {
                        Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                        startActivity(intent);
                    })
                    .show();
        }
    }

    private void handleBuyNow() {
        if (currentProduct != null) {
            Intent intent = new Intent(ProductDetailActivity.this, PaymentActivity.class);
            intent.putExtra("product_for_purchase", currentProduct);
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
