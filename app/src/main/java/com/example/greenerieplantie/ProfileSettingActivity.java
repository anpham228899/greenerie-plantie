package com.example.greenerieplantie;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ProfileSettingActivity extends AppCompatActivity {

    private ImageView imgProfile;
    private EditText edtFullName, edtEmail, edtPhone, edtGender, edtDob;
    private Button btnSave, btnCancel, btnEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);

        imgProfile = findViewById(R.id.imgProfile);
        edtFullName = findViewById(R.id.edtFullName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtGender = findViewById(R.id.edtGender);
        edtDob = findViewById(R.id.edtDob);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);

        changeLanguage("vi");

        btnCancel.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> {
            String fullName = edtFullName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String gender = edtGender.getText().toString().trim();
            String dob = edtDob.getText().toString().trim();

            if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty() || gender.isEmpty() || dob.isEmpty()) {
                Toast.makeText(ProfileSettingActivity.this, getString(R.string.fill_fields), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProfileSettingActivity.this, getString(R.string.profile_saved), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnEditProfile.setOnClickListener(v -> {
            edtFullName.setEnabled(true);
            edtEmail.setEnabled(true);
            edtPhone.setEnabled(true);
            edtGender.setEnabled(true);
            edtDob.setEnabled(true);
        });
    }

    private void changeLanguage(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        recreate();
    }
}