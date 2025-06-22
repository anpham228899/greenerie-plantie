package com.example.greenerieplantie;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ProfileSettingActivity extends AppCompatActivity {

    private ImageView imgProfile;
    private EditText edtFullName, edtEmail, edtPhone, edtGender, edtDob;
    private Button btnSave, btnCancel;

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
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        edtFullName.addTextChangedListener(fieldWatcher);
        edtEmail.addTextChangedListener(fieldWatcher);
        edtPhone.addTextChangedListener(fieldWatcher);
        edtGender.addTextChangedListener(fieldWatcher);
        edtDob.addTextChangedListener(fieldWatcher);

        updateSaveButtonState();

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
        ImageView btnEditImage = findViewById(R.id.btnEditImage);

        btnEditImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            imgProfile.setImageURI(imageUri);
        }
    }

    private void updateSaveButtonState() {
        String fullName = edtFullName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String gender = edtGender.getText().toString().trim();
        String dob = edtDob.getText().toString().trim();

        btnSave.setEnabled(!fullName.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !gender.isEmpty() && !dob.isEmpty());
    }

    private TextWatcher fieldWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            updateSaveButtonState();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

}