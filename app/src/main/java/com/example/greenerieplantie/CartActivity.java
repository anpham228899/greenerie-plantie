package com.example.greenerieplantie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;

import adapters.CartAdapter;
import models.Cart;
import utils.ListCart; 

public class CartActivity extends AppCompatActivity {

    private TextView cartItemCountTextView, totalPriceTextView;
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private CheckBox selectAllCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        cartItemCountTextView = findViewById(R.id.tv_cart_item_count);
        totalPriceTextView = findViewById(R.id.tv_total_value);

        recyclerView = findViewById(R.id.item_cart_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartAdapter = new CartAdapter(ListCart.getCartItems());
        recyclerView.setAdapter(cartAdapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        selectAllCheckbox = findViewById(R.id.cb_select_all);
        selectAllCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {

            for (Cart cartItem : ListCart.getCartItems()) {
                cartItem.setSelected(isChecked);
            }
            cartAdapter.notifyDataSetChanged();
            refreshCartDisplay();

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshCartDisplay();
    }

    public void refreshCartDisplay() {
        int itemCount = 0;
        double totalPrice = 0;

        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        for (Cart cartItem : ListCart.getCartItems()) {
            if (cartItem.isSelected()) {
                itemCount += cartItem.getQuantity();

                String priceString = cartItem.getPriceAfterDiscount().replace("VND", "").replace(",", "").trim();

                try {
                    double price = Double.parseDouble(priceString);
                    totalPrice += cartItem.getQuantity() * price;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        cartItemCountTextView.setText(String.valueOf(itemCount));

        totalPriceTextView.setText("VND " + decimalFormat.format(totalPrice));

        cartAdapter.notifyDataSetChanged();

        if (ListCart.getCartItems().isEmpty()) {
            selectAllCheckbox.setChecked(false);
            selectAllCheckbox.setEnabled(false);
        } else {
            selectAllCheckbox.setEnabled(true);
            boolean allSelected = true;
            for (Cart cartItem : ListCart.getCartItems()) {
                if (!cartItem.isSelected()) {
                    allSelected = false;
                    break;
                }
            }
            selectAllCheckbox.setChecked(allSelected);
        }
    }
}