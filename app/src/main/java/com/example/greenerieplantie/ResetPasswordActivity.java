package com.example.greenerieplantie;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.mindrot.jbcrypt.BCrypt;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText newPasswordInput, confirmPasswordInput;
    private Button resetPasswordButton;
    private TextView errorMessage, goBackText;

    private String email; // Email nhận từ Intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Nhận email từ intent
        email = getIntent().getStringExtra("email");
        Log.d("DEBUG_RESET", "Email passed to ResetPasswordActivity: " + email);


        // Khởi tạo view
        newPasswordInput = findViewById(R.id.edt_reset_password_input_password);
        confirmPasswordInput = findViewById(R.id.edt_sign_reset_password_confirm_password);
        resetPasswordButton = findViewById(R.id.btn_sign_up_verify);
        errorMessage = findViewById(R.id.title_reset_password_invalid);
        goBackText = findViewById(R.id.text_reset_password_go_back);
        goBackText.setPaintFlags(goBackText.getPaintFlags() | android.graphics.Paint.UNDERLINE_TEXT_FLAG);

        resetPasswordButton.setOnClickListener(v -> handleResetPassword());

        goBackText.setOnClickListener(v -> {
            Intent intent = new Intent(ResetPasswordActivity.this, ForgetPasswordActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
            finish();
        });
    }

    private void handleResetPassword() {
        String newPassword = newPasswordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();

        if (newPassword.length() < 6) {
            showError("Password must be at least 6 characters.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showError("Passwords do not match. Please try again.");
            return;
        }

        // Mã hóa mật khẩu
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        // Ghi vào database
        FirebaseDatabase.getInstance().getReference("users").get()
                .addOnSuccessListener(snapshot -> {
                    boolean found = false;
                    for (DataSnapshot child : snapshot.getChildren()) {
                        String userEmail = child.child("email").getValue(String.class);
                        if (userEmail != null && userEmail.equalsIgnoreCase(email)) {
                            // Cập nhật mật khẩu mới
                            DatabaseReference userRef = child.getRef();
                            userRef.child("password").setValue(hashedPassword);
                            found = true;
                            showPasswordResetSuccessPopup();
                            break;
                        }
                    }

                    if (!found) {
                        showError("User not found.");
                    }
                })
                .addOnFailureListener(error -> {
                    showError("Database error: " + error.getMessage());
                });
    }

    private void showError(String message) {
        errorMessage.setVisibility(View.VISIBLE);
        errorMessage.setText(message);
    }

    private void showPasswordResetSuccessPopup() {
        View popupView = getLayoutInflater().inflate(R.layout.popup_password_reset, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(popupView);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        popupView.setOnClickListener(v -> {
            startActivity(new Intent(ResetPasswordActivity.this, SignInActivity.class));
            finish();
        });

        dialog.show();
    }
}
