package com.example.greenerieplantie;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.regex.Pattern;

public class ShippingInformationActivity extends AppCompatActivity {

    private EditText etFullName, etEmail, etPhone;
    private Button btnCancel, btnSave;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shipping_information);

        etFullName = findViewById(R.id.et_full_name);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> onBackPressed());

        btnCancel.setOnClickListener(v -> {
            etFullName.setText("");
            etEmail.setText("");
            etPhone.setText("");
        });

        btnSave.setOnClickListener(v -> {
            String fullName = etFullName.getText().toString();
            String email = etEmail.getText().toString();
            String phone = etPhone.getText().toString();

            if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(ShippingInformationActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else if (!isValidPhoneNumber(phone)) {
                Toast.makeText(ShippingInformationActivity.this, "Please enter a valid 10-digit phone number", Toast.LENGTH_SHORT).show();
            } else if (!isValidEmail(email)) {
                Toast.makeText(ShippingInformationActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            } else {
                showDialog(fullName, email, phone);
            }
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ShippingInformationActivity.this, PaymentActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showDialog(String fullName, String email, String phone) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_save_shipping_info);
        dialog.setCancelable(true);

        TextView tvUpdatedInfo = dialog.findViewById(R.id.tv_infoupdated);

        String info = fullName + ", " + email + ", " + phone;
        tvUpdatedInfo.setText(info);

        ImageView imgClose = dialog.findViewById(R.id.img_close);
        imgClose.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private boolean isValidPhoneNumber(String phone) {
        return phone.matches("\\d{10}");
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
        return Pattern.matches(emailPattern, email);
    }
}
