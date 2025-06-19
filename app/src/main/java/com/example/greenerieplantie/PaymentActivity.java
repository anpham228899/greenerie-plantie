package com.example.greenerieplantie;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapters.CartAdapter;
import models.Cart;
import utils.Discount;
import utils.ListCart;

public class PaymentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private List<Cart> cartItems;

    private RadioButton rbStandard, rbExpress;
    private RadioButton rbCOD, rbBanking;

    private EditText etDiscountCode;
    private TextView tvDiscountApplied;
    private TextView tvDiscountValue, tvOrderTotalValue, tvTotalValue;

    private double orderTotal = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);

        // Initialize discount codes
        Discount.initDiscounts();

        // Init cart data
        cartItems = ListCart.getSampleCartData();

        // RecyclerView setup
        recyclerView = findViewById(R.id.item_cart_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CartAdapter(cartItems);
        recyclerView.setAdapter(adapter);

        // UI padding handling
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Shipping & Payment RadioButtons
        rbStandard = findViewById(R.id.rb_standard);
        rbExpress = findViewById(R.id.rb_express);
        rbCOD = findViewById(R.id.rb_cod);
        rbBanking = findViewById(R.id.rb_banking);

        rbStandard.setChecked(true);
        rbCOD.setChecked(true);

        rbStandard.setOnClickListener(v -> {
            if (rbStandard.isChecked()) rbExpress.setChecked(false);
        });
        rbExpress.setOnClickListener(v -> {
            if (rbExpress.isChecked()) rbStandard.setChecked(false);
        });
        rbCOD.setOnClickListener(v -> {
            if (rbCOD.isChecked()) rbBanking.setChecked(false);
        });
        rbBanking.setOnClickListener(v -> {
            if (rbBanking.isChecked()) rbCOD.setChecked(false);
        });

        // Edit buttons
        ImageView editShippingAddress = findViewById(R.id.img_edit_img_shipping_address);
        ImageView editInformation = findViewById(R.id.img_edit_phone);

        editShippingAddress.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentActivity.this, ShippingAddressActivity.class);
            startActivity(intent);
        });

        editInformation.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentActivity.this, ShippingInformationActivity.class);
            startActivity(intent);
        });

        // Discount Code UI
        etDiscountCode = findViewById(R.id.et_discount_code);
        tvDiscountApplied = findViewById(R.id.tv_discount_applied);
        tvDiscountValue = findViewById(R.id.tv_discount_value);
        tvOrderTotalValue = findViewById(R.id.tv_order_total_value);
        tvTotalValue = findViewById(R.id.tv_total_value);

        tvDiscountApplied.setVisibility(TextView.GONE);

        // Calculate and display order total initially
        calculateAndDisplayOrderTotal();

        // Handle discount code input
        etDiscountCode.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_NEXT) {
                String enteredCode = etDiscountCode.getText().toString().trim();
                Discount discount = Discount.getDiscountByCode(enteredCode);

                if (discount != null) {
                    tvDiscountApplied.setVisibility(TextView.VISIBLE);
                    tvDiscountApplied.setText("Discount applied");

                    double discountPercentage = discount.getDiscountPercentage();
                    tvDiscountValue.setText(String.format("%.0f%%", discountPercentage));

                    // Apply discount
                    double discountedTotal = orderTotal * (1 - discountPercentage / 100);
                    String discountedText = String.format("VND %,d", (int) discountedTotal);
                    tvTotalValue.setText(discountedText);

                    Toast.makeText(PaymentActivity.this,
                            "Applied " + discountPercentage + "% discount!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    tvDiscountApplied.setVisibility(TextView.GONE);
                    tvDiscountValue.setText("0%");
                    tvTotalValue.setText(tvOrderTotalValue.getText());

                    Toast.makeText(PaymentActivity.this,
                            "Invalid discount code",
                            Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });

        // Place Order Button
        Button btnPlaceOrder = findViewById(R.id.btn_place_order);
        btnPlaceOrder.setOnClickListener(v -> {
            showOrderPlacedDialog();
        });
    }

    private void calculateAndDisplayOrderTotal() {
        orderTotal = 0.0;

        for (Cart item : cartItems) {
            try {
                String raw = item.getPriceAfterDiscount()
                        .replace("VND", "")
                        .replace(",", "")
                        .trim();

                double price = Double.parseDouble(raw);
                orderTotal += price * item.getQuantity();
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(this, "Price format error: " + item.getPriceAfterDiscount(), Toast.LENGTH_SHORT).show();
            }
        }

        String formattedTotal = String.format("VND %,d", (int) orderTotal);
        tvOrderTotalValue.setText(formattedTotal);
        tvTotalValue.setText(formattedTotal);
    }

    private void showOrderPlacedDialog() {
        Dialog dialog = new Dialog(PaymentActivity.this);
        dialog.setContentView(R.layout.dialog_order_placed);
        dialog.setCancelable(false);  // Optional: make dialog not dismissible by touch outside

        // Get the ImageView for closing the dialog (Close button)
        ImageView btnCloseDialog = dialog.findViewById(R.id.img_close);
        btnCloseDialog.setOnClickListener(v -> dialog.dismiss()); // Dismiss dialog on click

        dialog.show();
    }

    private boolean isShippingOptionSelected() {
        return rbStandard.isChecked() || rbExpress.isChecked();
    }

    private boolean isPaymentMethodSelected() {
        return rbCOD.isChecked() || rbBanking.isChecked();
    }
}
