package com.example.greenerieplantie;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import connectors.UserConnector;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput, confirmPasswordInput;
    private Button sendVerificationCodeButton;
    private TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_sign_up);

        emailInput = findViewById(R.id.edt_sign_up_input_email);
        passwordInput = findViewById(R.id.edt_sign_up_input_password);
        confirmPasswordInput = findViewById(R.id.edt_sign_up_confirm_password);
        sendVerificationCodeButton = findViewById(R.id.btn_sign_up_send_verification_code);
        errorMessage = findViewById(R.id.title_sign_up_invalid);

        sendVerificationCodeButton.setOnClickListener(v -> handleSignUp());

        TextView textView = findViewById(R.id.text_sign_up_term_policy);
        String text = "By signing up, you will agree to our Terms and Policy.";
        SpannableString spannableString = new SpannableString(text);

        ClickableSpan termsClick = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(SignUpActivity.this, PoliciesActivity.class));
            }
        };

        ClickableSpan policyClick = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(SignUpActivity.this, PoliciesActivity.class));
            }
        };

        int termsStart = text.indexOf("Terms");
        int termsEnd = termsStart + "Terms".length();
        spannableString.setSpan(termsClick, termsStart, termsEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int policyStart = text.indexOf("Policy");
        int policyEnd = policyStart + "Policy".length();
        spannableString.setSpan(policyClick, policyStart, policyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(Color.TRANSPARENT);
        textView.setLinkTextColor(Color.parseColor("#517B2C"));
    }

    private void handleSignUp() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError("Invalid email format.");
            return;
        }

        if (password.length() < 6) {
            showError("Password must be at least 6 characters.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showError("Passwords do not match. Please try again.");
            return;
        }

        new UserConnector().getAllUsers(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    models.User user = child.getValue(models.User.class);
                    if (user != null && user.getEmail().equalsIgnoreCase(email)) {
                        showError("Email already exists.");
                        return;
                    }
                }

                // Nếu email chưa tồn tại → gửi mã xác minh
                String verificationCode = generateVerificationCode();
                sendVerificationCodeEmail(email, password, verificationCode);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                showError("Database error: " + error.getMessage());
            }
        });
    }

    private void showError(String message) {
        errorMessage.setVisibility(View.VISIBLE);
        errorMessage.setText(message);
    }

    private String generateVerificationCode() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }

    private void sendVerificationCodeEmail(String email, String password, String verificationCode) {
        new SendVerificationEmailTask(SignUpActivity.this, email, verificationCode, () -> {
            Intent intent = new Intent(SignUpActivity.this, SignUpVerifyActivity.class);
            intent.putExtra("email", email);
            intent.putExtra("password", password); // Mã hoá sau
            intent.putExtra("verificationCode", verificationCode);
            startActivity(intent);
            finish();
        }).execute();
    }
}
