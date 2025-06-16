package com.example.greenerieplantie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.graphics.Paint; // Thêm import này
import androidx.appcompat.app.AppCompatActivity;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText emailInput;
    private Button sendCodeButton;
    private TextView errorMessage, goBackText;

    private static final String SAMPLE_EMAIL = "user@example.com"; // Tài khoản mẫu để kiểm tra

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        // Ẩn thanh ActionBar nếu có
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Khởi tạo các thành phần giao diện
        emailInput = findViewById(R.id.edt_verify_code_code);
        sendCodeButton = findViewById(R.id.btn_verify_code_reset_password);
        errorMessage = findViewById(R.id.title_reset_password_invalid); // Đảm bảo TextView có id 'title_forget_password_email_invalid'
        goBackText = findViewById(R.id.text_verify_code_go_back); // TextView cho dòng chữ "Go back"

        // Áp dụng gạch chân cho chữ "Go back"
        goBackText.setPaintFlags(goBackText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // Gửi mã reset mật khẩu
        sendCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSendCode();
            }
        });

        // Xử lý sự kiện quay lại màn hình đăng nhập khi người dùng ấn vào "Go back"
        goBackText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại màn hình SignInActivity
                Intent intent = new Intent(ForgetPasswordActivity.this, SignInActivity.class);
                startActivity(intent);
                finish(); // Kết thúc ForgetPasswordActivity
            }
        });
    }

    // Xử lý khi nhấn nút gửi mã
    private void handleSendCode() {
        String email = emailInput.getText().toString().trim();

        if (isValidEmail(email)) {
            // Giả lập gửi mã code qua email
            errorMessage.setVisibility(View.GONE); // Ẩn thông báo lỗi
            // Chuyển sang màn hình tiếp theo để người dùng nhập mã code
            Intent intent = new Intent(ForgetPasswordActivity.this, VerifyCodeActivity.class);
            startActivity(intent);
            finish(); // Kết thúc màn hình hiện tại
        } else {
            // Hiển thị thông báo lỗi nếu email không hợp lệ
            errorMessage.setVisibility(View.VISIBLE);
            errorMessage.setText("Email invalid. Please try again.");
        }
    }

    // Kiểm tra email hợp lệ
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}

