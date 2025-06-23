package com.example.greenerieplantie;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
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

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

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

        // Gắn layout giao diện
        setContentView(R.layout.activity_sign_up);

        // Khởi tạo các thành phần
        emailInput = findViewById(R.id.edt_sign_up_input_email);
        passwordInput = findViewById(R.id.edt_sign_up_input_password);
        confirmPasswordInput = findViewById(R.id.edt_sign_up_confirm_password);
        sendVerificationCodeButton = findViewById(R.id.btn_sign_up_send_verification_code);
        errorMessage = findViewById(R.id.title_sign_up_invalid);

        // Xử lý khi nhấn nút "Send verification code"
        sendVerificationCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignUp();
            }
        });

        // Khởi tạo TextView cho Terms và Policy
        TextView textView = findViewById(R.id.text_sign_up_term_policy);

        // Nội dung văn bản hiển thị
        String text = "By signing up, you will agree to our Terms and Policy.";

        SpannableString spannableString = new SpannableString(text);

        // Sự kiện khi nhấn vào "Terms"
        ClickableSpan termsClick = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(SignUpActivity.this, PoliciesActivity.class);
                startActivity(intent);
            }
        };

        // Sự kiện khi nhấn vào "Policy"
        ClickableSpan policyClick = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(SignUpActivity.this, PoliciesActivity.class);
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
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(Color.TRANSPARENT); // Không hiển thị nền khi click
        textView.setLinkTextColor(Color.parseColor("#517B2C"));
    }

    // Xử lý đăng ký và kiểm tra mật khẩu
    private void handleSignUp() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();

        if (password.equals(confirmPassword)) {
            // Nếu hai mật khẩu giống nhau, gửi mã xác minh
            String verificationCode = generateVerificationCode(); // Tạo mã xác minh

            // Gửi mã xác minh qua email
            sendVerificationCodeEmail(email, verificationCode);

            // Chuyển sang màn hình nhập mã xác minh
            errorMessage.setVisibility(View.GONE); // Ẩn thông báo lỗi
            Intent intent = new Intent(SignUpActivity.this, SignUpVerifyActivity.class);
            intent.putExtra("verificationCode", verificationCode); // Truyền mã xác minh cho màn hình tiếp theo
            startActivity(intent);
            finish(); // Kết thúc màn hình hiện tại
        } else {
            // Nếu mật khẩu không giống nhau, hiển thị thông báo lỗi
            errorMessage.setVisibility(View.VISIBLE);
            errorMessage.setText("Passwords do not match. Please try again.");
        }
    }

    // Tạo mã xác minh ngẫu nhiên
    private String generateVerificationCode() {
        return String.valueOf((int) (Math.random() * 900000) + 100000); // Tạo mã gồm 6 chữ số ngẫu nhiên
    }

    // Gửi mã xác minh qua email (sử dụng AsyncTask để gửi email trong background thread)
    private void sendVerificationCodeEmail(String email, String verificationCode) {
        // Thực hiện gửi email trong background thread
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                String senderEmail = "hoangt22411ca@st.uel.edu.vn"; // Thay bằng email của bạn
                String senderPassword = "ofks blyj ufcy cphn";

                Properties properties = new Properties();
                properties.put("mail.smtp.host", "smtp.gmail.com");
                properties.put("mail.smtp.port", "465");
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.socketFactory.port", "465");
                properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

                Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(senderEmail, senderPassword);
                    }
                });

                try {
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(senderEmail));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                    message.setSubject("Verification Code");
                    message.setText("Your verification code is: " + verificationCode);

                    // Gửi email
                    Transport.send(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute(); // Thực thi AsyncTask
    }
}


