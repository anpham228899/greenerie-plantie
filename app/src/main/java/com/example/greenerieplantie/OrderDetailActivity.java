package com.example.greenerieplantie;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.greenerieplantie.R;

import java.util.List;

import adapters.OrderItemAdapter;
import connectors.OrderConnector;
import models.Order;
import models.OrderItem;

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
        new OrderConnector().getAllOrders(new OrderConnector.OrderCallback() {
            @Override
            public void onOrdersLoaded(List<Order> orders) {
                for (Order order : orders) {
                    if (order.orderId != null && order.orderId.equals(orderId)) {
                        bindOrderToView(order);
                        return;
                    }
                }
                Toast.makeText(OrderDetailActivity.this, "Order not found", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(OrderDetailActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bindOrderToView(Order order) {
        txtOrderNumber.setText("#" + order.orderId);
        txtOrderDate.setText(order.createdAt);
        updateDeliveryStage(order.status);

        OrderItemAdapter = new OrderItemAdapter(order.orderItems);
        rcvItemOrderDetail.setAdapter(OrderItemAdapter);

        // Giả sử bạn có shipping_fee, discount, tax_amount trong Firebase
        double subtotal = 0;
        for (OrderItem item : order.orderItems) {
            try {
                subtotal += Double.parseDouble(item.getPrice());
            } catch (Exception ignored) {}
        }

        double shippingFee = order.shippingInfo != null ? 50000 : 0;
        double discount = order.paymentInfo != null && order.paymentInfo.amount < subtotal ? subtotal - order.paymentInfo.amount : 0;
        double total = order.totalAmount;

        edtSubtotal.setText(String.format("%,.0f", subtotal));
        edtShippingFee.setText(String.format("%,.0f", shippingFee));
        textView5.setText(String.format("%,.0f", discount));
        edtTotal.setText(String.format("%,.0f", total));
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
                .setPositiveButton("Yes", (dialog, id) ->
                        Toast.makeText(OrderDetailActivity.this, "Order cancelled", Toast.LENGTH_SHORT).show())
                .setNegativeButton("No", null)
                .show();
    }

    private void showSuccessNotification() {
        Toast.makeText(OrderDetailActivity.this, "Review submitted successfully", Toast.LENGTH_SHORT).show();
    }
}
