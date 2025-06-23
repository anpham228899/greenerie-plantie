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
        btnSubmitReview = findViewById(R.id.btnSave);
        deliveryProgressBar = findViewById(R.id.barDeliveryProgress);
        rcvItemOrderDetail = findViewById(R.id.rcvItemOrderDetail);

        String orderNumber = getIntent().getStringExtra("order_number");
        String orderDate = getIntent().getStringExtra("order_date");
        orderItems = getIntent().getParcelableArrayListExtra("order_items");
        String orderStatus = getIntent().getStringExtra("order_status");

        txtOrderNumber.setText(orderNumber);
        txtOrderDate.setText(orderDate);

        rcvItemOrderDetail.setLayoutManager(new LinearLayoutManager(this));
        orderItemAdapter = new OrderItemAdapter(orderItems);
        rcvItemOrderDetail.setAdapter(orderItemAdapter);

        updateDeliveryStage(orderStatus);

        btnCancel.setOnClickListener(v -> showCancelConfirmationDialog());
        btnSubmitReview.setOnClickListener(v -> showSuccessNotification());

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

    private void updateDeliveryStage(String orderStatus) {
        if (orderStatus == null) orderStatus = "preparing";

        switch (orderStatus) {
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
