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

import java.text.DecimalFormat;  // Import for formatting numbers
import java.util.List;

import adapters.CartAdapter;
import models.Cart;
import utils.ListCart;

public class CartActivity extends AppCompatActivity {

    private TextView cartItemCountTextView, totalPriceTextView;
    private RecyclerView recyclerView;
    private List<Cart> cartItems;
    private CartAdapter cartAdapter;
    private CheckBox selectAllCheckbox;  // Checkbox to select all items

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge display
        EdgeToEdge.enable(this);

        // Set content view
        setContentView(R.layout.activity_cart);

        // Initialize views
        cartItemCountTextView = findViewById(R.id.cart_item_count);
        totalPriceTextView = findViewById(R.id.tv_total_value);  // TextView for total price
        recyclerView = findViewById(R.id.item_cart_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the cart data
        cartItems = ListCart.getSampleCartData();

        // Set up the RecyclerView with CartAdapter
        cartAdapter = new CartAdapter(cartItems);
        recyclerView.setAdapter(cartAdapter);

        // Initialize the cart item count
        updateCartItemCount();

        // Handle window insets for Edge-to-Edge mode
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the "select all" checkbox
        selectAllCheckbox = findViewById(R.id.select_all_checkbox);
        selectAllCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            for (Cart cartItem : cartItems) {
                cartItem.setSelected(isChecked);
            }
            cartAdapter.notifyDataSetChanged();
            updateCartItemCount();  // Update cart item count and total
        });
    }

    // Method to update the cart item count and total price
    public void updateCartItemCount() {
        int itemCount = 0;
        double totalPrice = 0;

        // Create a DecimalFormat instance to format the total price with commas
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        // Calculate the total number of items and the total price for selected items
        for (Cart cartItem : cartItems) {
            if (cartItem.isSelected()) {
                itemCount += cartItem.getQuantity();

                // Remove "VND" and commas before parsing the price
                String priceString = cartItem.getPriceAfterDiscount().replace("VND", "").replace(",", "").trim();

                try {
                    // Parse the cleaned-up string into a double value
                    double price = Double.parseDouble(priceString);
                    totalPrice += cartItem.getQuantity() * price;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        // Update the cart item count and total price
        cartItemCountTextView.setText("You have " + itemCount + " items in your cart");

        // Format the total price with commas and update the total price display
        totalPriceTextView.setText("VND " + decimalFormat.format(totalPrice));
    }
}
