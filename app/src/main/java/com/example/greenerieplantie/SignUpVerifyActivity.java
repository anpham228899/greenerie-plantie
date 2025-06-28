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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.UUID;

public class SignUpVerifyActivity extends AppCompatActivity {

    private EditText verificationCodeInput;
    private Button verifyButton;
    private TextView errorMessage;
    private TextView termsPolicyText;

    private String correctVerificationCode;
    private String email;
    private String rawPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_verify);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        verificationCodeInput = findViewById(R.id.edt_sign_up_verify_input_code);
        verifyButton = findViewById(R.id.btn_sign_up_verify);
        errorMessage = findViewById(R.id.title_sign_up_verify_invalid);
        termsPolicyText = findViewById(R.id.text_sign_up_verify_term_policy);

        // Lấy dữ liệu từ SignUpActivity
        correctVerificationCode = getIntent().getStringExtra("verificationCode");
        email = getIntent().getStringExtra("email");
        rawPassword = getIntent().getStringExtra("password");

        verifyButton.setOnClickListener(v -> handleVerification());

        setupTermsText();
    }

    private void handleVerification() {
        String enteredCode = verificationCodeInput.getText().toString().trim();

        if (!enteredCode.equals(correctVerificationCode)) {
            errorMessage.setVisibility(View.VISIBLE);
            errorMessage.setText("Invalid verification code. Please try again.");
            return;
        }

        // Mã đúng → hash mật khẩu
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt(10));

        // Tạo user mới và ghi vào Firebase
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
        String userId = UUID.randomUUID().toString();

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("email", email);
        userMap.put("password", hashedPassword);
        userMap.put("role", "user");
        userMap.put("name", "");
        userMap.put("address", "");
        userMap.put("phoneNumber", "");
        userMap.put("profilePicture", "");

        usersRef.child(userId).setValue(userMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(SignUpVerifyActivity.this, "Account created successfully!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(SignUpVerifyActivity.this, SignInActivity.class));
                finish();
            } else {
                errorMessage.setVisibility(View.VISIBLE);
                errorMessage.setText("Failed to create account: " + task.getException().getMessage());
            }
        });
    }

    private void setupTermsText() {
        String text = "By signing up, you will agree to our Terms and Policy.";
        SpannableString spannableString = new SpannableString(text);

        ClickableSpan termsClick = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(SignUpVerifyActivity.this, PoliciesActivity.class));
            }
        };

        ClickableSpan policyClick = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(SignUpVerifyActivity.this, PoliciesActivity.class));
            }
        };

        int termsStart = text.indexOf("Terms");
        int termsEnd = termsStart + "Terms".length();
        spannableString.setSpan(termsClick, termsStart, termsEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int policyStart = text.indexOf("Policy");
        int policyEnd = policyStart + "Policy".length();
        spannableString.setSpan(policyClick, policyStart, policyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        termsPolicyText.setText(spannableString);
        termsPolicyText.setMovementMethod(LinkMovementMethod.getInstance());
        termsPolicyText.setHighlightColor(Color.TRANSPARENT);
        termsPolicyText.setLinkTextColor(Color.parseColor("#517B2C"));
    }
}
