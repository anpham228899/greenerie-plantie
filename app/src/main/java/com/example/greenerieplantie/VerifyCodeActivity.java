package com.example.greenerieplantie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VerifyCodeActivity extends AppCompatActivity {

    private EditText codeInput;
    private Button resetPasswordButton;
    private TextView errorMessage, goBackText;
    private String correctCode;  // Thêm biến để lưu mã code gửi qua Intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code); // Đảm bảo layout đúng
        // Ẩn thanh ActionBar nếu có
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Khởi tạo các thành phần giao diện
        codeInput = findViewById(R.id.edt_verify_code_code);
        resetPasswordButton = findViewById(R.id.btn_verify_code_reset_password);
        errorMessage = findViewById(R.id.title_reset_password_invalid);
        goBackText = findViewById(R.id.text_verify_code_go_back);

        // Áp dụng gạch chân cho chữ "Go back"
        goBackText.setPaintFlags(goBackText.getPaintFlags() | android.graphics.Paint.UNDERLINE_TEXT_FLAG);

        // Lấy mã code từ Intent
        correctCode = getIntent().getStringExtra("resetCode");

        // Xử lý khi nhấn nút "Reset Password"
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleVerifyCode();
            }
        });

        // Xử lý khi nhấn vào "Go back" để quay lại trang ForgetPasswordActivity
        goBackText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerifyCodeActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
                finish(); // Kết thúc màn hình VerifyCodeActivity
            }
        });
    }

    // Xử lý khi nhấn nút "Reset Password" để xác thực mã code
    private void handleVerifyCode() {
        String code = codeInput.getText().toString().trim();

        if (isValidCode(code)) {
            // Nếu mã code hợp lệ
            errorMessage.setVisibility(View.GONE); // Ẩn thông báo lỗi
            // Chuyển sang màn hình nhập mật khẩu mới
            Intent intent = new Intent(VerifyCodeActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
            finish(); // Kết thúc màn hình hiện tại
        } else {
            // Hiển thị thông báo lỗi nếu mã code không đúng
            errorMessage.setVisibility(View.VISIBLE);
            errorMessage.setText("Code invalid. Please try again.");
        }
    }

    // Kiểm tra mã code hợp lệ
    private boolean isValidCode(String code) {
        return code.equals(correctCode); // So sánh với mã code nhận được từ Intent
    }
}



