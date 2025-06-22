package com.example.greenerieplantie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button signInButton, forgotPasswordButton;
    private TextView errorMessage;
    private CheckBox rememberMe;
    private SharedPreferences sharedPreferences;

    private static final String SAMPLE_EMAIL = "hoang2k4@gmail.com";
    private static final String SAMPLE_PASSWORD = "hoang01062004";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        emailInput = findViewById(R.id.edt_verify_code_code);
        passwordInput = findViewById(R.id.edt_sign_in_password);
        signInButton = findViewById(R.id.btn_verify_code_reset_password);
        errorMessage = findViewById(R.id.title_reset_password_invalid); // Đảm bảo TextView có id 'error_message'
        rememberMe = findViewById(R.id.checkbox_remember_me);
        forgotPasswordButton = findViewById(R.id.btn_sign_in_forogt_password);

        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignIn();
            }
        });

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void handleSignIn() {
        // Lấy dữ liệu người dùng nhập vào
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        // Kiểm tra email và mật khẩu hợp lệ
        if (isValidEmail(email) && isValidPassword(password)) {
            // Kiểm tra tài khoản mẫu
            if (email.equals(SAMPLE_EMAIL) && password.equals(SAMPLE_PASSWORD)) {
                // Đăng nhập thành công
                errorMessage.setVisibility(View.GONE); // Ẩn thông báo lỗi
                // Lưu trạng thái đăng nhập nếu "Remember me" được chọn
                if (rememberMe.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", email);
                    editor.putString("password", password);
                    editor.apply();
                }

                // Chuyển tới màn hình HomepageActivity sau khi đăng nhập thành công
                Intent intent = new Intent(SignInActivity.this, HomepageActivity.class);
                startActivity(intent);
                finish(); // Kết thúc SignInActivity để người dùng không quay lại màn hình đăng nhập
            } else {
                // Hiển thị thông báo lỗi
                errorMessage.setVisibility(View.VISIBLE);
                errorMessage.setText("Email or password invalid. Please try again.");
            }
        } else {
            // Nếu không hợp lệ
            errorMessage.setVisibility(View.VISIBLE);
            errorMessage.setText("Email or password invalid. Please try again.");
        }
    }

    // Kiểm tra email hợp lệ
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Kiểm tra mật khẩu hợp lệ
    private boolean isValidPassword(String password) {
        return password.length() > 6; // Mật khẩu phải dài hơn 6 ký tự
    }
}
