package com.example.greenerieplantie;

import android.os.Bundle;
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
import java.util.List;

import adapters.CartAdapter;
import models.Cart;
import utils.ListCart;

public class CartActivity extends AppCompatActivity {

    private TextView cartItemCountTextView, totalPriceTextView;
    private RecyclerView recyclerView;
    private List<Cart> cartItems;
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

        cartItems = ListCart.getSampleCartData();

        cartAdapter = new CartAdapter(cartItems);
        recyclerView.setAdapter(cartAdapter);

        updateCartItemCount();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        selectAllCheckbox = findViewById(R.id.cb_select_all);
        selectAllCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            for (Cart cartItem : cartItems) {
                cartItem.setSelected(isChecked);
            }
            cartAdapter.notifyDataSetChanged();
            updateCartItemCount();
        });
    }

    public void updateCartItemCount() {
        int itemCount = 0;
        double totalPrice = 0;

        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        for (Cart cartItem : cartItems) {
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
    }
}