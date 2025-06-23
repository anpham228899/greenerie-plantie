package com.example.greenerieplantie;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.graphics.Paint; // Thêm import này
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText emailInput;
    private Button sendCodeButton;
    private TextView errorMessage, goBackText;

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
        errorMessage = findViewById(R.id.title_reset_password_invalid);
        goBackText = findViewById(R.id.text_verify_code_go_back);

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
            // Nếu email hợp lệ, gửi mã reset
            errorMessage.setVisibility(View.GONE); // Ẩn thông báo lỗi
            String resetCode = generateResetCode(); // Tạo mã reset

            // Gửi mã qua email trong AsyncTask
            new SendResetCodeTask().execute(email, resetCode);  // Thực thi AsyncTask để gửi email

            // Chuyển sang màn hình tiếp theo để người dùng nhập mã code
            Intent intent = new Intent(ForgetPasswordActivity.this, VerifyCodeActivity.class);
            intent.putExtra("resetCode", resetCode);  // Truyền mã reset cho màn hình tiếp theo
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

    // Tạo mã reset ngẫu nhiên
    private String generateResetCode() {
        return String.valueOf((int) (Math.random() * 900000) + 100000); // Tạo mã gồm 6 chữ số ngẫu nhiên
    }

    // AsyncTask để gửi email trong background thread
    private class SendResetCodeTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            String email = params[0];
            String resetCode = params[1];

            sendResetCodeEmail(email, resetCode);  // Gửi email trong background

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Sau khi gửi email, hiển thị Toast hoặc thực hiện các hành động khác
            Toast.makeText(ForgetPasswordActivity.this, "Reset code sent to your email.", Toast.LENGTH_SHORT).show();
        }
    }

    // Gửi email chứa mã reset
    private void sendResetCodeEmail(String email, String resetCode) {
        String senderEmail = "hoangt22411ca@st.uel.edu.vn"; // Thay bằng email của bạn
        String senderPassword = "ofks blyj ufcy cphn"; // Sử dụng mật khẩu ứng dụng nếu bạn sử dụng Gmail với xác minh 2 bước

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Password Reset Code");
            message.setText("Your password reset code is: " + resetCode);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}




