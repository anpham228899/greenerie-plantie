package com.example.greenerieplantie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import adapters.CartAdapter;
import connectors.CartConnector;
import models.Cart;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView cartItemCount, tvCartItemCountText, tvItemsText, tvTotalValue;
    private CheckBox cbSelectAll;
    private Button btnCheckout;

    private List<Cart> cartList = new ArrayList<>();
    private CartAdapter cartAdapter;
    private String currentUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_cart);



        // Lấy UID hiện tại (có thể thay thế nếu bạn không dùng FirebaseAuth)
        SharedPreferences prefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        currentUid = prefs.getString("user_uid", null);

        if (currentUid == null) {
            Toast.makeText(this, "Không tìm thấy phiên đăng nhập!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Ánh xạ View theo XML
        recyclerView = findViewById(R.id.item_cart_recycler_view);
        cartItemCount = findViewById(R.id.cart_item_count);
        tvCartItemCountText = findViewById(R.id.tv_cart_item_count); // "You have"
        tvItemsText = findViewById(R.id.txtItems); // "items in your cart"
        tvTotalValue = findViewById(R.id.tv_total_value);
        cbSelectAll = findViewById(R.id.cb_select_all);
        btnCheckout = findViewById(R.id.btn_checkout);

        // Thiết lập RecyclerView
        cartAdapter = new CartAdapter(cartList, this, currentUid,true, new CartAdapter.OnCartChangeListener() {
            @Override
            public void onQuantityChanged() {
                calculateTotal();
            }

            @Override
            public void onItemRemoved(Cart removedItem) {
                calculateTotal();
                updateCartCount();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cartAdapter);

        // Load dữ liệu giỏ hàng
        loadCartItems();

        // Select All checkbox (có thể dùng sau)
        cbSelectAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            CartConnector connector = new CartConnector(currentUid);
            for (Cart item : cartList) {
                item.setSelected(isChecked); // cập nhật local
                connector.updateSelection(item.getProduct_id(), isChecked); // cập nhật Firebase
            }
            cartAdapter.notifyDataSetChanged(); // cập nhật giao diện
            calculateTotal(); // cập nhật tổng tiền
        });

        // Xử lý khi click checkout
        btnCheckout.setOnClickListener(v -> {
            List<Cart> selectedItems = new ArrayList<>();
            for (Cart item : cartList) {
                if (item.isSelected()) {
                    selectedItems.add(item);
                }
            }

            if (selectedItems.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn sản phẩm để thanh toán", Toast.LENGTH_SHORT).show();
                return;
            }

            // Chuyển sang PaymentActivity – dữ liệu sẽ được đọc lại từ Firebase theo uid
            Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
            startActivity(intent);
        });
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        NavMenuActivity.setupNavMenu(bottomNav, this, R.id.nav_cart);
        bottomNav.setSelectedItemId(R.id.nav_cart);
    }

    private void loadCartItems() {
        CartConnector connector = new CartConnector(currentUid);
        connector.getCartItems(new CartConnector.CartLoadCallback() {
            @Override
            public void onCartLoaded(List<Cart> items) {
                cartList.clear();
                cartList.addAll(items);
                cartAdapter.notifyDataSetChanged();
                updateCartCount();
                calculateTotal();

                if (cartList.isEmpty()) {
                    tvItemsText.setText("Giỏ hàng trống");
                    cbSelectAll.setEnabled(false);
                    btnCheckout.setEnabled(false);
                } else {
                    tvItemsText.setText("items in your cart");
                    cbSelectAll.setEnabled(true);
                    btnCheckout.setEnabled(true);
                }
            }

            @Override
            public void onCartLoadFailed(Exception e) {
                Toast.makeText(CartActivity.this, "Không tải được giỏ hàng: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateCartCount() {
        cartItemCount.setText(String.valueOf(cartList.size()));
    }

    private void calculateTotal() {
        int total = 0;
        for (Cart item : cartList) {
            if (item.isSelected()) { // nếu bạn muốn chỉ tính item được chọn
                total += item.getProduct_price() * item.getQuantity();
            }
        }
        tvTotalValue.setText(formatCurrency(total));
    }

    private String formatCurrency(int amount) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return nf.format(amount).replace("₫", "VND");
    }
}
