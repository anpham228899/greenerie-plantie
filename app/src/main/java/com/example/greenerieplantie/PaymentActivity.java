package com.example.greenerieplantie;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapters.CartAdapter;
import models.Cart;
import models.Product;
import utils.Discount;
import utils.ListCart;

public class PaymentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private List<Cart> cartItemsForPayment;

    private RadioButton rbStandard, rbExpress;
    private RadioButton rbCOD, rbBanking;

    private EditText etDiscountCode;
    private TextView tvDiscountApplied;
    private TextView tvDiscountValue, tvOrderTotalValue, tvTotalValue;

    private double orderTotal = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);

        Discount.initDiscounts();

        cartItemsForPayment = new ArrayList<>();

        if (getIntent().hasExtra("product_for_purchase")) {
            Product productToBuy = getIntent().getParcelableExtra("product_for_purchase");
            if (productToBuy != null) {
                cartItemsForPayment.add(new Cart(
                        productToBuy.getProductId(),
                        productToBuy.getName(),
                        productToBuy.getCategory(),
                        "VND " + productToBuy.getFormattedOriginalPrice(),
                        "VND " + productToBuy.getFormattedPrice(),
                        1,
                        productToBuy.getImageResId()
                ));
            }
        } else {
            for (Cart item : ListCart.getCartItems()) {
                if (item.isSelected()) {
                    cartItemsForPayment.add(item);
                }
            }
            if (cartItemsForPayment.isEmpty()) {
                Toast.makeText(this, "No items selected for payment. Returning to cart.", Toast.LENGTH_LONG).show();
                finish();
                return;
            }
        }

        recyclerView = findViewById(R.id.item_cart_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CartAdapter(cartItemsForPayment);
        recyclerView.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rbStandard = findViewById(R.id.rb_standard);
        rbExpress = findViewById(R.id.rb_express);
        rbCOD = findViewById(R.id.rb_cod);
        rbBanking = findViewById(R.id.rb_banking);

        rbStandard.setChecked(true);
        rbCOD.setChecked(true);

        rbStandard.setOnClickListener(v -> {
            if (rbStandard.isChecked()) rbExpress.setChecked(false);
        });
        rbExpress.setOnClickListener(v -> {
            if (rbExpress.isChecked()) rbStandard.setChecked(false);
        });
        rbCOD.setOnClickListener(v -> {
            if (rbCOD.isChecked()) rbBanking.setChecked(false);
        });
        rbBanking.setOnClickListener(v -> {
            if (rbBanking.isChecked()) rbCOD.setChecked(false);
        });

        ImageView editShippingAddress = findViewById(R.id.img_edit_img_shipping_address);
        ImageView editInformation = findViewById(R.id.img_edit_phone);

        editShippingAddress.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentActivity.this, ShippingAddressActivity.class);
            startActivity(intent);
        });

        editInformation.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentActivity.this, ShippingInformationActivity.class);
            startActivity(intent);
        });

        etDiscountCode = findViewById(R.id.et_discount_code);
        tvDiscountApplied = findViewById(R.id.tv_discount_applied);
        tvDiscountValue = findViewById(R.id.tv_discount_value);
        tvOrderTotalValue = findViewById(R.id.tv_order_total_value);
        tvTotalValue = findViewById(R.id.tv_total_value);

        tvDiscountApplied.setVisibility(TextView.GONE);

        calculateAndDisplayOrderTotal();

        etDiscountCode.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_NEXT) {
                String enteredCode = etDiscountCode.getText().toString().trim();
                Discount discount = Discount.getDiscountByCode(enteredCode);

                if (discount != null) {
                    tvDiscountApplied.setVisibility(TextView.VISIBLE);
                    tvDiscountApplied.setText("Discount applied");

                    double discountPercentage = discount.getDiscountPercentage();
                    tvDiscountValue.setText(String.format("%.0f%%", discountPercentage));

                    double discountedTotal = orderTotal * (1 - discountPercentage / 100);
                    String discountedText = String.format("VND %,d", (int) discountedTotal);
                    tvTotalValue.setText(discountedText);

                    Toast.makeText(PaymentActivity.this,
                            "Applied " + discountPercentage + "% discount!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    tvDiscountApplied.setVisibility(TextView.GONE);
                    tvDiscountValue.setText("0%");
                    tvTotalValue.setText(tvOrderTotalValue.getText());

                    Toast.makeText(PaymentActivity.this,
                            "Invalid discount code",
                            Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });

        Button btnPlaceOrder = findViewById(R.id.btn_place_order);
        btnPlaceOrder.setOnClickListener(v -> {
            showOrderPlacedDialog();
        });
    }

    private void calculateAndDisplayOrderTotal() {
        orderTotal = 0.0;

        for (Cart item : cartItemsForPayment) {
            try {
                String raw = item.getPriceAfterDiscount()
                        .replace("VND", "")
                        .replace(",", "")
                        .trim();

                double price = Double.parseDouble(raw);
                orderTotal += price * item.getQuantity();
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(this, "Price format error: " + item.getPriceAfterDiscount(), Toast.LENGTH_SHORT).show();
            }
        }

        String formattedTotal = String.format("VND %,d", (int) orderTotal);
        tvOrderTotalValue.setText(formattedTotal);
        tvTotalValue.setText(formattedTotal);
    }

    private void showOrderPlacedDialog() {
        Dialog dialog = new Dialog(PaymentActivity.this);
        dialog.setContentView(R.layout.dialog_order_placed);
        dialog.setCancelable(false);

        ImageView btnCloseDialog = dialog.findViewById(R.id.img_close);
        btnCloseDialog.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private boolean isShippingOptionSelected() {
        return rbStandard.isChecked() || rbExpress.isChecked();
    }

    private boolean isPaymentMethodSelected() {
        return rbCOD.isChecked() || rbBanking.isChecked();
    }
}