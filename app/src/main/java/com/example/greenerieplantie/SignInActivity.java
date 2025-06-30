package com.example.greenerieplantie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import org.mindrot.jbcrypt.BCrypt;
import androidx.appcompat.app.AppCompatActivity;

import connectors.UserConnector;

public class SignInActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button signInButton, forgotPasswordButton;
    private TextView errorMessage;
    private CheckBox rememberMe;
    private SharedPreferences sharedPreferences;


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
        String savedEmail = sharedPreferences.getString("email", "");
        String savedPassword = sharedPreferences.getString("password", "");
        if (!savedEmail.isEmpty() && !savedPassword.isEmpty()) {
            emailInput.setText(savedEmail);
            passwordInput.setText(savedPassword);
            rememberMe.setChecked(true);
        }
    }

    private void handleSignIn() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            showError("Please fill all fields.");
            return;
        }

        new UserConnector().getAllUsers(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                boolean isFound = false;

                for (DataSnapshot child : snapshot.getChildren()) {
                    models.User user = child.getValue(models.User.class);
                    if (user == null) {
                        Log.e("DEBUG", "User object is null, skip this child");
                        continue;
                    }
                    Log.d("DEBUG", "User email: " + user.getEmail());
                    Log.d("DEBUG", "User password: " + user.getPassword());
                    if (user.getEmail() == null || user.getPassword() == null) {
                        Log.e("DEBUG", "User email or password is null, skip");
                        continue;
                    }
                    user.setUid(child.getKey());
                    String fixedHash = user.getPassword().replaceFirst("^\\$2b\\$", "\\$2a\\$");
                    if (user.getEmail().equals(email) && BCrypt.checkpw(password, fixedHash)) {
                        isFound = true;
                        models.User.currentUser = user;
                        sharedPreferences.edit()
                                .putString("user_uid", user.getUid())
                                .apply();
                        Log.d("DEBUG", "Saved UID: " + user.getUid());

                        if (rememberMe.isChecked()) {
                            sharedPreferences.edit()
                                    .putString("email", email)
                                    .putString("password", password)
                                    .apply();
                        }

                        startActivity(new Intent(SignInActivity.this, HomepageActivity.class));
                        finish();
                        break;
                    }
                }

                if (!isFound) {
                    showError("Email or password incorrect.");
                }
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
}
