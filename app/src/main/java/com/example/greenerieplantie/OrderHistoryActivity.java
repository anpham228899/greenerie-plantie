package com.example.greenerieplantie;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapters.OrderAdapter;
import models.Order;
import models.OrderItem;

public class OrderHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private ArrayList<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        recyclerView = findViewById(R.id.orderRecyclerView);

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        orderList = new ArrayList<>();

        List<OrderItem> orderItems1 = new ArrayList<>();
        orderItems1.add(new OrderItem("Red Apple Tree", R.mipmap.img_rice, "120000"));
        orderItems1.add(new OrderItem("Blueberry Plant", R.mipmap.img_rice, "50000"));

        List<OrderItem> orderItems2 = new ArrayList<>();
        orderItems2.add(new OrderItem("Cucumber Plant", R.mipmap.img_grapes, "50000"));

        orderList.add(new Order("#ORD123456", "25/5/2025", orderItems1, R.mipmap.img_rice));
        orderList.add(new Order("#ORD123457", "26/5/2025", orderItems2, R.mipmap.img_grapes));

        orderAdapter = new OrderAdapter(orderList, this);
        recyclerView.setAdapter(orderAdapter);
    }
}