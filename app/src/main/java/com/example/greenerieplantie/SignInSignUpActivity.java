package com.example.greenerieplantie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SignInSignUpActivity extends AppCompatActivity {

    private Button signInButton;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_signup);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        signInButton = findViewById(R.id.btn_verify_code_reset_password); // Nút Sign In
        signUpButton = findViewById(R.id.btn_signin_and_signup_signup); // Nút Sign Up


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến trang SignInActivity
                Intent signInIntent = new Intent(SignInSignUpActivity.this, SignInActivity.class);
                startActivity(signInIntent);
            }
        });


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signUpIntent = new Intent(SignInSignUpActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });
    }
}
