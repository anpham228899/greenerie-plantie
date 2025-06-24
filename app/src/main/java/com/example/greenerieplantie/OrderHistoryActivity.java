package com.example.greenerieplantie;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
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
        setContentView(R.layout.activity_order_history);

        recyclerView = findViewById(R.id.orderRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(orderList, this);
        recyclerView.setAdapter(orderAdapter);

        loadUserOrderHistory();
    }

    private void loadUserOrderHistory() {
        SharedPreferences prefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String userId = prefs.getString("user_uid", null);

        if (userId == null) {
            Log.e("OrderHistory", "User ID not found in SharedPreferences.");
            return;
        }

        OrderConnector connector = new OrderConnector();
        connector.getAllOrders(new OrderConnector.OrderCallback() {
            @Override
            public void onOrdersLoaded(List<Order> orders) {
                orderList.clear();
                for (Order order : orders) {
                    if (order.userId != null && order.userId.equals(userId)) {
                        orderList.add(order);
                    }
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
