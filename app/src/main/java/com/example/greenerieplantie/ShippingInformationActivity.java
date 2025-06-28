package com.example.greenerieplantie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import models.User;

public class ShippingInformationActivity extends AppCompatActivity {

    private EditText etFullName, etEmail, etPhone;
    private Button btnCancel, btnSave;
    private ImageView btnBack;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_information);

        // Ánh xạ view
        etFullName = findViewById(R.id.et_full_name);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        // Lấy UID từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        userId = prefs.getString("user_uid", null);

        // Hiển thị dữ liệu từ currentUser
        if (User.currentUser != null) {
            etFullName.setText(User.currentUser.getName());
            etEmail.setText(User.currentUser.getEmail());
            etPhone.setText(User.currentUser.getPhoneNumber());
        }

        // Quay lại
        btnBack.setOnClickListener(v -> finish());

        // Xoá dữ liệu
        btnCancel.setOnClickListener(v -> {
            etFullName.setText("");
            etEmail.setText("");
            etPhone.setText("");
        });

        // Lưu lại Firebase
        btnSave.setOnClickListener(v -> {
            String name = etFullName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (userId == null) {
                Toast.makeText(this, "Không xác định được người dùng", Toast.LENGTH_SHORT).show();
                return;
            }

            // Cập nhật vào User.currentUser
            User.currentUser.setName(name);
            User.currentUser.setEmail(email);
            User.currentUser.setPhoneNumber(phone);

            // Lưu lên Firebase
            FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(userId)
                    .setValue(User.currentUser)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(this, "Đã lưu thông tin giao hàng!", Toast.LENGTH_SHORT).show();
                        finish(); // Quay lại màn trước
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Lỗi lưu thông tin", Toast.LENGTH_SHORT).show();
                        Log.e("FirebaseError", "Lỗi khi lưu user info", e);
                    });
        });
    }
}
