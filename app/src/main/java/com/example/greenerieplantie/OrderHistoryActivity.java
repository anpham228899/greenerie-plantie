package com.example.greenerieplantie;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import adapters.OrderAdapter;
import connectors.OrderConnector;
import models.Order;
import models.User;

public class OrderHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("OrderHistory", "onCreate called");
        setContentView(R.layout.activity_order_history);

        recyclerView = findViewById(R.id.orderRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderList = new ArrayList<>();

        orderAdapter = new OrderAdapter(orderList, this);
        Log.d("OrderHistory", "Số lượng đơn hàng: " + orderList.size());
        recyclerView.setAdapter(orderAdapter);

        loadUserOrderHistory();
    }

    private void loadUserOrderHistory() {
        SharedPreferences prefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String userId = prefs.getString("user_uid", null);

        Log.d("OrderHistory", "UID từ SharedPreferences: " + userId);

        if (userId == null) {
            Log.e("OrderHistory", "User ID not found in SharedPreferences.");
            return;
        }

        OrderConnector connector = new OrderConnector();
        connector.getOrdersByUserId(userId, new OrderConnector.OrderCallback() {
            @Override
            public void onOrdersLoaded(List<Order> orders) {
                orderList.clear();
                Collections.sort(orders, (o1, o2) -> Long.compare(o2.createdAt, o1.createdAt));
                orderList.addAll(orders);
                Log.d("OrderHistory", "Tổng đơn hàng: " + orderList.size());
                if (orders.isEmpty()) {
                    Toast.makeText(OrderHistoryActivity.this, "Bạn chưa có đơn hàng nào", Toast.LENGTH_SHORT).show();
                }
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("OrderHistoryActivity", errorMessage);
            }
        });
    }
}
