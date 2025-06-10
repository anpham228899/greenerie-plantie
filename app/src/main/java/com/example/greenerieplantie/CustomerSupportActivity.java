package com.example.greenerieplantie;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class CustomerSupportActivity extends AppCompatActivity {

    private ImageView btnBack;
    private TextView edtHotline;
    private Button btnChatbot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_support);

        edtHotline = findViewById(R.id.txtHotlineNumber);
        btnBack = findViewById(R.id.btnBack);
        btnChatbot = findViewById(R.id.btnChatbot);

        edtHotline.setOnClickListener(v -> {
            String phoneNumber = edtHotline.getText().toString();

            new AlertDialog.Builder(CustomerSupportActivity.this)
                    .setMessage("Do you want to call this number: " + phoneNumber + "?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            makePhoneCall(phoneNumber);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        btnBack.setOnClickListener(v -> onBackPressed());
        btnChatbot.setOnClickListener(v -> {
            Intent intent = new Intent(CustomerSupportActivity.this, ChatbotActivity.class);
            startActivity(intent);
        });
    }

    private void makePhoneCall(String phoneNumber) {
        if (!phoneNumber.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        } else {
            Toast.makeText(this, "Phone number is empty", Toast.LENGTH_SHORT).show();
        }
    }
}