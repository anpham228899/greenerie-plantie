package com.example.greenerieplantie;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import android.content.SharedPreferences;
import models.ShippingInfo;
import models.User;

import androidx.appcompat.app.AppCompatActivity;

public class ShippingAddressActivity extends AppCompatActivity {

    private AutoCompleteTextView actvCountry, actvCity;
    private EditText etDistrict, etStreet, etNumber;
    private Button btnCancel, btnSave;
    private ImageView btnBack;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);

        // Initialize views
        actvCountry = findViewById(R.id.actv_country);
        actvCity = findViewById(R.id.actv_city);
        etDistrict = findViewById(R.id.et_district);
        etStreet = findViewById(R.id.et_street);
        etNumber = findViewById(R.id.et_number);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);
        TextView tvShippingAddress = findViewById(R.id.tv_shipping_address);
        // Set up AutoCompleteTextViews
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.country)
        );
        actvCountry.setAdapter(countryAdapter);

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.provinces_name)
        );
        actvCity.setAdapter(cityAdapter);

        // Lấy userId từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        userId = prefs.getString("user_uid", null);

        // Load shipping info từ Firebase
        if (userId != null) {
            FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(userId)
                    .child("shipping_info")
                    .get()
                    .addOnSuccessListener(snapshot -> {
                        if (snapshot.exists()) {
                            ShippingInfo info = snapshot.getValue(ShippingInfo.class);
                            if (info != null) {
                                actvCountry.setText(info.getProvince());
                                actvCity.setText(info.getDistrict());
                                etDistrict.setText(info.getWard());

                                String fullAddress = info.getAddress();
                                if (fullAddress != null && fullAddress.contains(",")) {
                                    String[] parts = fullAddress.split(",", 2);
                                    etStreet.setText(parts[0].trim());
                                    etNumber.setText(parts[1].trim());
                                } else {
                                    etStreet.setText(fullAddress);
                                    etNumber.setText("");
                                }
                            }
                        }
                    });
        }

        // Cancel clears all inputs
        btnCancel.setOnClickListener(v -> {
            actvCountry.setText("");
            actvCity.setText("");
            etDistrict.setText("");
            etStreet.setText("");
            etNumber.setText("");
        });

        btnSave.setOnClickListener(v -> {
            String country = actvCountry.getText().toString().trim();
            String city = actvCity.getText().toString().trim();
            String district = etDistrict.getText().toString().trim();
            String street = etStreet.getText().toString().trim();
            String number = etNumber.getText().toString().trim();

            if (country.isEmpty() || city.isEmpty() || district.isEmpty() || street.isEmpty() || number.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (userId == null) {
                Toast.makeText(this, "Không xác định được người dùng", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = (User.currentUser != null) ? User.currentUser.getName() : "";
            String phone = (User.currentUser != null) ? User.currentUser.getPhoneNumber() : "";
            String email = (User.currentUser != null) ? User.currentUser.getEmail() : "";

            ShippingInfo shippingInfo = new ShippingInfo(
                    name,
                    phone,
                    email,
                    street + ", " + number,
                    district,
                    city,
                    country,
                    "" // notes
            );

            FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(userId)
                    .child("shipping_info")
                    .setValue(shippingInfo)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(this, "Đã lưu địa chỉ giao hàng!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ShippingAddressActivity.this, PaymentActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Lỗi lưu địa chỉ", Toast.LENGTH_SHORT).show();
                        Log.e("FirebaseError", "Lỗi lưu shipping_info", e);
                    });
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ShippingAddressActivity.this, PaymentActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }
}
