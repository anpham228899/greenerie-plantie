package com.example.greenerieplantie;
import android.app.Dialog;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenerieplantie.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import adapters.CartAdapter;
import connectors.CartConnector;
import models.Cart;

public class PaymentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private List<Cart> cartItems = new ArrayList<>();

    private RadioButton rbStandard, rbExpress;
    private RadioButton rbCOD, rbBanking;
    private EditText etDiscountCode;
    private TextView tvDiscountApplied, tvDiscountValue, tvOrderTotalValue, tvTotalValue;

    private double orderTotal = 0.0;
    private double discountValue = 0.0;
    private String selectedPaymentMethod = "COD";
    private String selectedShippingMethod = "Standard";

    private String currentUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);

        SharedPreferences prefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        currentUid = prefs.getString("user_uid", null);

        if (currentUid == null) {
            Toast.makeText(this, "Không tìm thấy phiên đăng nhập!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Ánh xạ view
        recyclerView = findViewById(R.id.item_cart_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        rbStandard = findViewById(R.id.rb_standard);
        rbExpress = findViewById(R.id.rb_express);
        rbCOD = findViewById(R.id.rb_cod);
        rbBanking = findViewById(R.id.rb_banking);
        etDiscountCode = findViewById(R.id.et_discount_code);
        tvDiscountApplied = findViewById(R.id.tv_discount_applied);
        tvDiscountValue = findViewById(R.id.tv_discount_value);
        tvOrderTotalValue = findViewById(R.id.tv_order_total_value);
        tvTotalValue = findViewById(R.id.tv_total_value);
        Button btnPlaceOrder = findViewById(R.id.btn_place_order);

        rbStandard.setChecked(true);
        rbCOD.setChecked(true);
        tvDiscountApplied.setVisibility(TextView.GONE);

        rbStandard.setOnClickListener(v -> {
            if (rbStandard.isChecked()) {
                rbExpress.setChecked(false);
                selectedShippingMethod = "Standard";
            }
        });

        rbExpress.setOnClickListener(v -> {
            if (rbExpress.isChecked()) {
                rbStandard.setChecked(false);
                selectedShippingMethod = "Express";
            }
        });

        rbCOD.setOnClickListener(v -> {
            if (rbCOD.isChecked()) {
                rbBanking.setChecked(false);
                selectedPaymentMethod = "COD";
            }
        });

        rbBanking.setOnClickListener(v -> {
            if (rbBanking.isChecked()) {
                rbCOD.setChecked(false);
                selectedPaymentMethod = "Banking";
            }
        });

        // Load giỏ hàng từ Firebase
        new CartConnector(currentUid).getCartItems(new CartConnector.CartLoadCallback() {
            @Override
            public void onCartLoaded(List<Cart> items) {
                cartItems.clear();
                for (Cart item : items) {
                    if (item.isSelected()) cartItems.add(item);
                }

                adapter = new CartAdapter(cartItems, PaymentActivity.this, currentUid, new CartAdapter.OnCartChangeListener() {
                    @Override
                    public void onQuantityChanged() {
                        calculateTotal();
                    }

                    @Override
                    public void onItemRemoved(Cart removedItem) {
                        cartItems.remove(removedItem);
                        adapter.notifyDataSetChanged();
                        calculateTotal();
                    }
                });

                recyclerView.setAdapter(adapter);
                calculateTotal();
            }

            @Override
            public void onCartLoadFailed(Exception e) {
                Toast.makeText(PaymentActivity.this, "Lỗi tải giỏ hàng", Toast.LENGTH_SHORT).show();
            }
        });

        // Mã giảm giá (đơn giản)
        etDiscountCode.setOnEditorActionListener((v, actionId, event) -> {
            String enteredCode = etDiscountCode.getText().toString().trim();
            if (enteredCode.equalsIgnoreCase("SALE10")) {
                discountValue = 0.10;
                tvDiscountApplied.setVisibility(TextView.VISIBLE);
                tvDiscountApplied.setText("Đã áp dụng mã giảm giá!");
                tvDiscountValue.setText("10%");
                calculateTotal();
            } else {
                discountValue = 0.0;
                tvDiscountApplied.setVisibility(TextView.GONE);
                tvDiscountValue.setText("0%");
                calculateTotal();
            }
            return true;
        });

        // Đặt hàng (demo dialog)
        btnPlaceOrder.setOnClickListener(v -> showOrderPlacedDialog());
    }

    private void calculateTotal() {
        orderTotal = 0.0;

        for (Cart item : cartItems) {
            orderTotal += item.getProduct_price() * item.getQuantity();
        }

        double discountedTotal = orderTotal * (1 - discountValue);
        String formatted = String.format("VND %,d", (int) discountedTotal);

        tvOrderTotalValue.setText(String.format("VND %,d", (int) orderTotal));
        tvTotalValue.setText(formatted);
    }

    private void showOrderPlacedDialog() {
        Dialog dialog = new Dialog(PaymentActivity.this);
        dialog.setContentView(R.layout.dialog_order_placed);
        dialog.setCancelable(false);
        ImageView btnCloseDialog = dialog.findViewById(R.id.img_close);
        btnCloseDialog.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}
