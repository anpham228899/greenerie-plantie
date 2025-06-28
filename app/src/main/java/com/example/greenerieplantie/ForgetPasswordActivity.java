package com.example.greenerieplantie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.graphics.Paint;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;



public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText emailInput;
    private Button sendCodeButton;
    private TextView errorMessage, goBackText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        emailInput = findViewById(R.id.edt_verify_code_code);
        sendCodeButton = findViewById(R.id.btn_verify_code_reset_password);
        errorMessage = findViewById(R.id.title_reset_password_invalid);
        goBackText = findViewById(R.id.text_verify_code_go_back);
        goBackText.setPaintFlags(goBackText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        sendCodeButton.setOnClickListener(v -> handleSendCode());
        goBackText.setOnClickListener(v -> finish());
    }

    private void handleSendCode() {
        String email = emailInput.getText().toString().trim();

        if (!isValidEmail(email)) {
            showError("Invalid email format.");
            return;
        }

        FirebaseDatabase.getInstance().getReference("users").get().addOnSuccessListener(snapshot -> {
            for (DataSnapshot child : snapshot.getChildren()) {
                String userEmail = child.child("email").getValue(String.class);
                if (userEmail != null && userEmail.equalsIgnoreCase(email)) {
                    String code = generateVerificationCode();

                    new SendVerificationEmailTask(
                            this,
                            email,
                            code,
                            () -> {
                                Intent intent = new Intent(this, VerifyCodeActivity.class);
                                intent.putExtra("email", email);
                                intent.putExtra("resetCode", code);
                                startActivity(intent);
                                finish();
                            }
                    ).execute();

                    return;
                }
            }
            showError("Email not found.");
        }).addOnFailureListener(error -> showError("Database error: " + error.getMessage()));
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private String generateVerificationCode() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }

    private void showError(String message) {
        errorMessage.setVisibility(View.VISIBLE);
        errorMessage.setText(message);
    }
}
