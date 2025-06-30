package com.example.greenerieplantie;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.greenerieplantie.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import adapters.OrderItemAdapter;
import connectors.OrderConnector;
import models.Order;
import models.OrderItem;
import models.PaymentInfo;

public class OrderDetailActivity extends AppCompatActivity {

    private TextView txtOrderNumber, txtOrderDate, txtDeliveryStage;
    private Button btnCancel, btnSubmitReview;
    private ProgressBar deliveryProgressBar;
    private RecyclerView rcvItemOrderDetail;
    private OrderItemAdapter OrderItemAdapter;

    private TextView edtSubtotal, edtShippingFee, textView5, edtTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        initViews();

        String orderId = getIntent().getStringExtra("orderId");
        if (orderId == null) {
            Toast.makeText(this, "Order ID not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadOrderFromFirebase(orderId);

        btnCancel.setOnClickListener(v -> showCancelConfirmationDialog());
        btnSubmitReview.setOnClickListener(v -> showSuccessNotification());
    }

    private void initViews() {
        txtOrderNumber = findViewById(R.id.edtOrderDetailID);
        txtOrderDate = findViewById(R.id.edtOrderDetailDate);
        txtDeliveryStage = findViewById(R.id.txtDeliveryStage);
        btnCancel = findViewById(R.id.btnCancel);
        btnSubmitReview = findViewById(R.id.btnSave);
        deliveryProgressBar = findViewById(R.id.barDeliveryProgress);
        rcvItemOrderDetail = findViewById(R.id.rcvItemOrderDetail);
        rcvItemOrderDetail.setLayoutManager(new LinearLayoutManager(this));

        edtSubtotal = findViewById(R.id.edtSubtotal);
        edtShippingFee = findViewById(R.id.edtShippingFee);
        textView5 = findViewById(R.id.textView5);
        edtTotal = findViewById(R.id.edtTotal);
    }

    private void loadOrderFromFirebase(String orderId) {
        String userId = getSharedPreferences("LoginPrefs", MODE_PRIVATE).getString("user_uid", null);
        if (userId == null) {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseDatabase.getInstance()
                .getReference("orders")
                .child(userId)
                .child(orderId)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()) {
                        Order order = new Order();
                        order.orderId = snapshot.child("orderId").getValue(String.class);
                        order.userId = snapshot.child("userId").getValue(String.class);
                        order.status = snapshot.child("status").getValue(String.class);
                        order.createdAt = snapshot.child("createdAt").getValue(Long.class) != null
                                ? snapshot.child("createdAt").getValue(Long.class) : 0L;
                        order.totalAmount = snapshot.child("totalAmount").getValue(Double.class) != null
                                ? snapshot.child("totalAmount").getValue(Double.class) : 0.0;
                        order.subtotal = snapshot.child("subtotal").getValue(Double.class) != null
                                ? snapshot.child("subtotal").getValue(Double.class) : 0.0;

                        // Parse payment_info
                        DataSnapshot paymentSnap = snapshot.child("payment_info");
                        if (paymentSnap.exists()) {
                            PaymentInfo pi = new PaymentInfo();
                            pi.setAmount(paymentSnap.child("amount").getValue(Double.class) != null
                                    ? paymentSnap.child("amount").getValue(Double.class) : 0.0);
                            pi.setDiscount(paymentSnap.child("discount").getValue(Double.class) != null
                                    ? paymentSnap.child("discount").getValue(Double.class) : 0.0);
                            pi.setShippingFee(paymentSnap.child("shippingFee").getValue(Double.class) != null
                                    ? paymentSnap.child("shippingFee").getValue(Double.class) : 0.0);
                            pi.setTotal(paymentSnap.child("total").getValue(Double.class) != null
                                    ? paymentSnap.child("total").getValue(Double.class) : 0.0);
                            pi.setMethod(paymentSnap.child("method").getValue(String.class));
                            pi.setStatus(paymentSnap.child("status").getValue(String.class));
                            order.setPaymentInfo(pi);
                        }

                        // Parse order_items
                        DataSnapshot itemsSnap = snapshot.child("order_items");
                        if (itemsSnap.exists()) {
                            Map<String, OrderItem> itemMap = new HashMap<>();
                            for (DataSnapshot itemSnap : itemsSnap.getChildren()) {
                                OrderItem item = itemSnap.getValue(OrderItem.class);
                                if (item != null) {
                                    itemMap.put(itemSnap.getKey(), item);
                                }
                            }
                            order.setOrderItems(itemMap);
                        }

                        // Gọi hàm bind ra UI
                        bindOrderToView(order);
                    } else {
                        Toast.makeText(this, "Order not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error loading order data", Toast.LENGTH_SHORT).show();
                });
    }


    private void bindOrderToView(Order order) {
        String shortOrderId = order.orderId;
        if (shortOrderId != null && shortOrderId.length() >= 6) {
            shortOrderId = shortOrderId.substring(shortOrderId.length() - 6);
        }
        txtOrderNumber.setText(shortOrderId);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String formattedDate = sdf.format(new Date(order.getCreatedAt()));
        txtOrderDate.setText(formattedDate);

        updateDeliveryStage(order.status);

        // Load item list
        List<OrderItem> itemList = new ArrayList<>();
        if (order.orderItems != null) {
            itemList.addAll(order.orderItems.values());
        }
        OrderItemAdapter = new OrderItemAdapter(itemList);
        rcvItemOrderDetail.setAdapter(OrderItemAdapter);

        // ✅ Lấy dữ liệu chính xác từ Firebase
        double subtotal = order.subtotal; // Đã được lưu từ PaymentActivity
        double shippingFee = order.paymentInfo != null ? order.paymentInfo.shippingFee : 0;
        double discount = order.paymentInfo != null ? order.paymentInfo.discount : 0;
        double total = order.totalAmount;

        edtSubtotal.setText(String.format("%,d VND", (int) subtotal));
        edtShippingFee.setText(String.format("%,d VND", (int) shippingFee));
        textView5.setText(String.format("- %,d VND", (int) discount));
        edtTotal.setText(String.format("%,d VND", (int) total));
        Log.d("shipping", "shippingFee from order = " + (order.paymentInfo != null ? order.paymentInfo.shippingFee : "null"));

    }


    private void updateDeliveryStage(String orderStatus) {
        if (orderStatus == null) orderStatus = "preparing";

        switch (orderStatus.toLowerCase()) {
            case "preparing":
                txtDeliveryStage.setText("Stage 1: Preparing for Shipment");
                deliveryProgressBar.setProgress(1);
                btnCancel.setEnabled(true);
                btnSubmitReview.setEnabled(false);
                break;
            case "shipping":
                txtDeliveryStage.setText("Stage 2: Shipping");
                deliveryProgressBar.setProgress(2);
                btnCancel.setEnabled(false);
                btnSubmitReview.setEnabled(false);
                break;
            case "delivered":
                txtDeliveryStage.setText("Stage 3: Delivered");
                deliveryProgressBar.setProgress(3);
                btnCancel.setEnabled(false);
                btnSubmitReview.setEnabled(true);
                break;
            case "cancelled":
                txtDeliveryStage.setText("Order Cancelled");
                deliveryProgressBar.setProgress(0);
                btnCancel.setEnabled(false);
                btnSubmitReview.setEnabled(false);
                break;
            default:
                txtDeliveryStage.setText("Unknown Status");
                deliveryProgressBar.setProgress(0);
                btnCancel.setEnabled(false);
                btnSubmitReview.setEnabled(false);
                break;
        }
    }

    private void showCancelConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Cancel Order")
                .setMessage("Are you sure you want to cancel this order?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> cancelOrderOnFirebase())
                .setNegativeButton("No", null)
                .show();
    }

    private void showSuccessNotification() {
        Toast.makeText(OrderDetailActivity.this, "Review submitted successfully", Toast.LENGTH_SHORT).show();
    }

    private void cancelOrderOnFirebase() {
        String orderId = getIntent().getStringExtra("orderId");
        if (orderId == null) return;

        String userId = getSharedPreferences("LoginPrefs", MODE_PRIVATE).getString("user_uid", null);
        if (userId == null) {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseDatabase.getInstance()
                .getReference("orders")
                .child(userId)
                .child(orderId)
                .child("status")
                .setValue("cancelled")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Order cancelled successfully", Toast.LENGTH_SHORT).show();
                        updateDeliveryStage("cancelled");
                    } else {
                        Toast.makeText(this, "Failed to cancel order", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
