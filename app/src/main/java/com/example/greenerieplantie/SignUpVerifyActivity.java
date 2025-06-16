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

import androidx.appcompat.app.AppCompatActivity;

public class SignUpVerifyActivity extends AppCompatActivity {

    private EditText verificationCodeInput;
    private Button verifyButton;
    private TextView errorMessage;
    private TextView termsPolicyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_verify);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Khởi tạo các thành phần
        verificationCodeInput = findViewById(R.id.edt_sign_up_verify_input_code);
        verifyButton = findViewById(R.id.btn_sign_up_verify);
        errorMessage = findViewById(R.id.title_sign_up_verify_invalid);
        termsPolicyText = findViewById(R.id.text_sign_up_verify_term_policy);

        // Xử lý khi nhấn nút "Verify"
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleVerification();
            }
        });

        // Khởi tạo TextView cho Terms và Policy
        String text = "By signing up, you will agree to our Terms and Policy.";
        SpannableString spannableString = new SpannableString(text);

        // Sự kiện khi nhấn vào "Terms"
        ClickableSpan termsClick = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(SignUpVerifyActivity.this, PoliciesActivity.class);
                startActivity(intent);
            }
        };

        // Sự kiện khi nhấn vào "Policy"
        ClickableSpan policyClick = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(SignUpVerifyActivity.this, PoliciesActivity.class);
                startActivity(intent);
            }
        };

        // Áp dụng span vào từ "Terms"
        int termsStart = text.indexOf("Terms");
        int termsEnd = termsStart + "Terms".length();
        spannableString.setSpan(termsClick, termsStart, termsEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Áp dụng span vào từ "Policy"
        int policyStart = text.indexOf("Policy");
        int policyEnd = policyStart + "Policy".length();
        spannableString.setSpan(policyClick, policyStart, policyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Gán spannable vào TextView
        termsPolicyText.setText(spannableString);
        termsPolicyText.setMovementMethod(LinkMovementMethod.getInstance());
        termsPolicyText.setHighlightColor(Color.TRANSPARENT); //
        termsPolicyText.setLinkTextColor(Color.parseColor("#517B2C"));
    }

    // Xử lý xác thực mã xác minh
    private void handleVerification() {
        String verificationCode = verificationCodeInput.getText().toString().trim();

        // Kiểm tra mã xác minh
        if (verificationCode.equals("123456")) {

            errorMessage.setVisibility(View.GONE); // Ẩn thông báo lỗi
            Intent intent = new Intent(SignUpVerifyActivity.this, HomepageActivity.class);
            startActivity(intent);
            finish(); // Kết thúc màn hình hiện tại
        } else {

            errorMessage.setVisibility(View.VISIBLE);
            errorMessage.setText("Invalid verification code. Please try again.");
        }
    }
}

