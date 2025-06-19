package com.example.greenerieplantie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText newPasswordInput, confirmPasswordInput;
    private Button resetPasswordButton;
    private TextView errorMessage, goBackText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password); // Đảm bảo layout đúng
        // Ẩn ActionBar nếu có
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        // Khởi tạo các thành phần
        newPasswordInput = findViewById(R.id.edt_reset_password_input_password);
        confirmPasswordInput = findViewById(R.id.edt_sign_reset_password_confirm_password);
        resetPasswordButton = findViewById(R.id.btn_sign_up_verify);
        errorMessage = findViewById(R.id.title_reset_password_invalid);
        goBackText = findViewById(R.id.text_reset_password_go_back);

        // Áp dụng gạch chân cho chữ "Go back"
        goBackText.setPaintFlags(goBackText.getPaintFlags() | android.graphics.Paint.UNDERLINE_TEXT_FLAG);

        // Xử lý khi nhấn nút "Reset Password"
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleResetPassword();
            }
        });

        // Xử lý khi nhấn vào "Go back" để quay lại trang ForgetPasswordActivity
        goBackText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPasswordActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
                finish(); // Kết thúc màn hình hiện tại
            }
        });
    }

    // Xử lý khi nhấn nút "Reset Password" để xác thực và thay đổi mật khẩu
    private void handleResetPassword() {
        String newPassword = newPasswordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();

        if (newPassword.equals(confirmPassword)) {
            // Nếu hai mật khẩu giống nhau, hiển thị thông báo thành công
            errorMessage.setVisibility(View.GONE); // Ẩn thông báo lỗi
            showPasswordResetSuccessPopup();
        } else {
            // Nếu mật khẩu không giống nhau, hiển thị thông báo lỗi
            errorMessage.setVisibility(View.VISIBLE);
            errorMessage.setText("Passwords do not match. Please try again.");
        }
    }

    // Hiển thị popup thông báo thành công và chuyển sang màn hình chủ
    private void showPasswordResetSuccessPopup() {
        // Tạo một AlertDialog với layout tùy chỉnh
        View popupView = getLayoutInflater().inflate(R.layout.popup_password_reset, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(popupView);


        // Tạo và hiển thị AlertDialog
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // Giảm màu nền của popup
        dialog.setCancelable(false); // Không cho phép tắt popup bằng cách nhấn ra ngoài

        // Khi nhấn vào bất kỳ đâu trong popup, chuyển tới màn hình SignInActivity
        popupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPasswordActivity.this, HomepageActivity.class);
                startActivity(intent);
                finish(); // Kết thúc màn hình hiện tại
            }
        });

        // Hiển thị popup
        dialog.show();
    }
}


