package com.example.greenerieplantie;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapters.OrderItemAdapter;
import models.OrderItem;

public class OrderDetailActivity extends AppCompatActivity {

    private TextView txtOrderNumber, txtOrderDate, txtDeliveryStage;
    private Button btnCancel, btnSubmitReview;
    private ProgressBar deliveryProgressBar;
    private RecyclerView rcvItemOrderDetail;
    private OrderItemAdapter orderItemAdapter;
    private ArrayList<OrderItem> orderItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        txtOrderNumber = findViewById(R.id.edtOrderDetailID);
        txtOrderDate = findViewById(R.id.edtOrderDetailDate);
        txtDeliveryStage = findViewById(R.id.txtDeliveryStage);
        btnCancel = findViewById(R.id.btnCancel);
        btnSubmitReview = findViewById(R.id.btnSubmitReview);
        deliveryProgressBar = findViewById(R.id.barDeliveryProgress);
        rcvItemOrderDetail = findViewById(R.id.rcvItemOrderDetail);

        // Get the order details passed from the intent
        String orderNumber = getIntent().getStringExtra("order_number");
        String orderDate = getIntent().getStringExtra("order_date");
        orderItems = getIntent().getParcelableArrayListExtra("order_items");

        // Set the order details on the UI
        txtOrderNumber.setText(orderNumber);
        txtOrderDate.setText(orderDate);

        // Set the RecyclerView layout manager and adapter for order items
        rcvItemOrderDetail.setLayoutManager(new LinearLayoutManager(this));
        orderItemAdapter = new OrderItemAdapter(orderItems);  // Use the OrderItemAdapter
        rcvItemOrderDetail.setAdapter(orderItemAdapter);

        // Update the delivery stage and progress
        updateDeliveryStage();

        // Cancel order dialog
        btnCancel.setOnClickListener(v -> showCancelConfirmationDialog());

        // Submit review success
        btnSubmitReview.setOnClickListener(v -> showSuccessNotification());

        // Set subtotal, shipping fee, discount, and total (hardcoded values)
        String subtotal = "800,000 VND";
        String shippingFee = "50,000 VND";
        String discount = "20,000 VND";
        String total = "830,000 VND";

        TextView edtSubtotal = findViewById(R.id.edtSubtotal);
        TextView edtShippingFee = findViewById(R.id.edtShippingFee);
        TextView textView5 = findViewById(R.id.textView5);
        TextView edtTotal = findViewById(R.id.edtTotal);

        edtSubtotal.setText(subtotal);
        edtShippingFee.setText(shippingFee);
        textView5.setText(discount);
        edtTotal.setText(total);
    }

    private void updateDeliveryStage() {
        // Set the delivery stage (hardcoded for now)
        txtDeliveryStage.setText("Stage 1: Preparing for Shipment");
        deliveryProgressBar.setProgress(1);
    }

    private void showCancelConfirmationDialog() {
        // Show a dialog for order cancellation confirmation
        new AlertDialog.Builder(this)
                .setTitle("Cancel Order")
                .setMessage("Are you sure you want to cancel this order?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> Toast.makeText(OrderDetailActivity.this, "Order cancelled", Toast.LENGTH_SHORT).show())
                .setNegativeButton("No", null)
                .show();
    }

    private void showSuccessNotification() {
        // Show success message when review is submitted
        Toast.makeText(OrderDetailActivity.this, "Review submitted successfully", Toast.LENGTH_SHORT).show();
    }
}
