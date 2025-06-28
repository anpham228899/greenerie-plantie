package com.example.greenerieplantie;
import android.app.Dialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import models.ShippingInfo;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import adapters.CartAdapter;
import connectors.CartConnector;
import models.Cart;
import models.OrderItem;
import models.User;

public class PaymentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private List<Cart> cartItems = new ArrayList<>();

    private RadioButton rbStandard, rbExpress;
    private RadioButton rbCOD, rbBanking;
    private EditText etDiscountCode;
    private TextView tvDiscountApplied, tvDiscountValue, tvOrderTotalValue, tvTotalValue, tvShippingAddress;

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
        findViewById(R.id.information_ll).setOnClickListener(v -> openShippingInformation());

// Ngoài onCreate(), trong class PaymentActivity

        // Ánh xạ view
        recyclerView = findViewById(R.id.item_cart_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvShippingAddress = findViewById(R.id.tv_shipping_address);

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

        // ✅ Phân biệt mua ngay vs giỏ hàng
        boolean isBuyNow = getIntent().getBooleanExtra("buy_now", false);
        String productId = getIntent().getStringExtra("product_id");

        if (isBuyNow && productId != null) {
            FirebaseDatabase.getInstance().getReference("products")
                    .child(productId)
                    .get()
                    .addOnSuccessListener(snapshot -> {
                        if (snapshot.exists()) {
                            Cart item = new Cart();
                            item.setProduct_id(productId);
                            item.setProduct_name(String.valueOf(snapshot.child("product_name").getValue()));
                            item.setCategory_id(String.valueOf(snapshot.child("category_id").getValue()));
                            item.setProduct_description(String.valueOf(snapshot.child("product_description").getValue()));
                            item.setProduct_discount(snapshot.child("product_discount").getValue(Integer.class));
                            item.setProduct_previous_price(snapshot.child("product_previous_price").getValue(Integer.class));
                            item.setProduct_price(snapshot.child("product_price").getValue(Integer.class));
                            item.setProduct_stock(snapshot.child("product_stock").getValue(Integer.class));
                            item.setQuantity(1);
                            item.setSelected(true);

                            Map<String, String> images = new HashMap<>();
                            for (DataSnapshot img : snapshot.child("product_images").getChildren()) {
                                images.put(img.getKey(), String.valueOf(img.getValue()));
                            }
                            item.setProduct_images(images);

                            cartItems.clear();
                            cartItems.add(item);

                            adapter = new CartAdapter(cartItems, PaymentActivity.this, currentUid, null);
                            recyclerView.setAdapter(adapter);
                            calculateTotal();

                        } else {
                            Toast.makeText(this, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Lỗi tải sản phẩm", Toast.LENGTH_SHORT).show();
                        finish();
                    });
        } else {
            // ✅ Load giỏ hàng như bình thường
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
        }

        // Mã giảm giá
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
        loadShippingInformation();
        // Đặt hàng
        btnPlaceOrder.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(currentUid)
                    .child("shipping_info")
                    .get()
                    .addOnSuccessListener(snapshot -> {
                        if (snapshot.exists()) {
                            ShippingInfo shippingInfo = snapshot.getValue(ShippingInfo.class);
                            placeOrderToFirebase(shippingInfo);  // ✅ Gọi bản có tham số
                        } else {
                            Toast.makeText(this, "Bạn chưa nhập địa chỉ giao hàng", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Lỗi khi kiểm tra địa chỉ giao hàng", Toast.LENGTH_SHORT).show();
                    });
        });
        loadShippingAddress();
    }
    private void openShippingInformation() {
        Intent intent = new Intent(PaymentActivity.this, ShippingInformationActivity.class);
        startActivity(intent);
    }
    private void placeOrderToFirebase(ShippingInfo shippingInfo) {
        String userId = currentUid;
        String orderId = UUID.randomUUID().toString();

        Map<String, OrderItem> itemMap = new HashMap<>();
        double subtotal = 0;

        for (Cart cart : cartItems) {
            OrderItem item = new OrderItem(
                    cart.getProduct_name(),
                    cart.getProduct_images().get("image1"),
                    String.valueOf(cart.getProduct_price()));
            itemMap.put(cart.getProduct_id(), item);
            subtotal += cart.getProduct_price() * cart.getQuantity();
        }


        SharedPreferences prefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        currentUid = prefs.getString("user_uid", null);
        if (currentUid != null) {
            FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(currentUid)
                    .child("shipping_info")
                    .get()
                    .addOnSuccessListener(snapshot -> {
                        if (snapshot.exists()) {
                            ShippingInfo info = snapshot.getValue(ShippingInfo.class);
                            if (info != null) {
                                String fullAddress = info.getAddress() + ", " +
                                        info.getWard() + ", " +
                                        info.getDistrict() + ", " +
                                        info.getProvince();
                                tvShippingAddress.setText(fullAddress);
                            }
                        } else {
                            tvShippingAddress.setText("Chưa có địa chỉ giao hàng");
                        }
                    })
                    .addOnFailureListener(e -> {
                        tvShippingAddress.setText("Lỗi tải địa chỉ");
                        Log.e("FirebaseError", "Không thể load shipping_info", e);
                    });
        }


        double shippingFee = selectedShippingMethod.equals("Standard") ? 50000 : 80000;
        double discountAmount = subtotal * discountValue;
        double total = subtotal + shippingFee - discountAmount;

        Map<String, Object> orderData = new HashMap<>();
        orderData.put("orderId", orderId);
        orderData.put("userId", userId);
        orderData.put("status", "preparing");
        orderData.put("createdAt", System.currentTimeMillis());
        orderData.put("totalAmount", total);
        orderData.put("order_items", itemMap);

        Map<String, Object> paymentInfo = new HashMap<>();
        paymentInfo.put("method", selectedPaymentMethod);
        paymentInfo.put("status", "unpaid");
        paymentInfo.put("discount", discountAmount);
        paymentInfo.put("shippingFee", shippingFee);
        paymentInfo.put("total", total);

        Map<String, Object> shippingMap = new HashMap<>();
        shippingMap.put("name", shippingInfo.getName());
        shippingMap.put("address", shippingInfo.getAddress());
        shippingMap.put("phone", shippingInfo.getPhone());
        shippingMap.put("email", shippingInfo.getEmail());
        shippingMap.put("ward", shippingInfo.getWard());
        shippingMap.put("district", shippingInfo.getDistrict());
        shippingMap.put("province", shippingInfo.getProvince());
        shippingMap.put("notes", shippingInfo.getNotes());

        orderData.put("payment_info", paymentInfo);
        orderData.put("shipping_info", shippingMap);

        FirebaseDatabase.getInstance()
                .getReference("orders")
                .child(userId)
                .child(orderId)
                .setValue(orderData)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        clearCart(userId);
                        gotoOrderDetail(orderId);
                    } else {
                        Toast.makeText(this, "Lỗi tạo đơn hàng", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadShippingAddress() {
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(currentUid)
                .child("shipping_info")
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        ShippingInfo info = snapshot.getValue(ShippingInfo.class);
                        if (info != null) {
                            String fullAddress = info.getAddress() + ", " +
                                    info.getWard() + ", " +
                                    info.getDistrict() + ", " +
                                    info.getProvince();
                            tvShippingAddress.setText(fullAddress);
                        }
                    } else {
                        tvShippingAddress.setText("Chưa có địa chỉ giao hàng");
                    }
                })
                .addOnFailureListener(e -> {
                    tvShippingAddress.setText("Lỗi tải địa chỉ");
                    Log.e("FirebaseError", "Không thể load shipping_info", e);
                });
    }
    public void editShippingAddress(View view) {
        Intent intent = new Intent(this, ShippingAddressActivity.class);
        startActivity(intent);
    }
    private void clearCart(String userId) {
        FirebaseDatabase.getInstance()
                .getReference("carts")
                .child(userId)
                .removeValue()
                .addOnSuccessListener(unused -> Toast.makeText(this, "Đơn hàng đã được đặt và giỏ hàng đã được xóa", Toast.LENGTH_SHORT).show());
    }
    private void loadShippingInformation() {
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(currentUid)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        String name = snapshot.child("name").getValue(String.class);
                        String phone = snapshot.child("phoneNumber").getValue(String.class);

                        TextView tvName = findViewById(R.id.tv_information_name);
                        TextView tvPhone = findViewById(R.id.tv_information_phone);

                        if (name != null && !name.isEmpty()) {
                            tvName.setText(name);
                        } else {
                            tvName.setText("Chưa có tên");
                        }

                        if (phone != null && !phone.isEmpty()) {
                            tvPhone.setText(phone);
                        } else {
                            tvPhone.setText("Chưa có số điện thoại");
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FirebaseError", "Lỗi tải thông tin người nhận", e);
                });
    }

    private void gotoOrderDetail(String orderId) {
        Intent intent = new Intent(PaymentActivity.this, OrderDetailActivity.class);
        intent.putExtra("orderId", orderId);
        startActivity(intent);
        finish();
    }
    private void gotoShippingInformation(String orderId) {
        Intent intent = new Intent(PaymentActivity.this, ShippingInformationActivity.class);
        intent.putExtra("orderId", orderId);
        startActivity(intent);
        finish();
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
