package com.example.greenerieplantie;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Dialog;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ShippingAddressActivity extends AppCompatActivity {

    private AutoCompleteTextView actvCountry, actvCity;
    private EditText etDistrict, etStreet, etNumber;
    private Button btnCancel, btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);

        // Initialize views
        actvCountry = findViewById(R.id.actv_country);
        actvCity = findViewById(R.id.actv_city);
        etDistrict = findViewById(R.id.et_district);
        etStreet = findViewById(R.id.et_street);
        etNumber = findViewById(R.id.et_number);
        btnCancel = findViewById(R.id.btn_cancel);
        btnSave = findViewById(R.id.btn_save);

        // Set up AutoCompleteTextViews with predefined values
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.country));
        actvCountry.setAdapter(countryAdapter);

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.provinces_name));
        actvCity.setAdapter(cityAdapter);

        // Handle button clicks
        btnCancel.setOnClickListener(v -> {
            // Clear all input fields
            actvCountry.setText("");
            actvCity.setText("");
            etDistrict.setText("");
            etStreet.setText("");
            etNumber.setText("");
        });

        btnSave.setOnClickListener(v -> showDialog());
    }

    private void showDialog() {
        String country = actvCountry.getText().toString();
        String city = actvCity.getText().toString();
        String district = etDistrict.getText().toString();
        String street = etStreet.getText().toString();
        String number = etNumber.getText().toString();

        if (country.isEmpty() || city.isEmpty() || district.isEmpty() || street.isEmpty() || number.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            // Create a new dialog and set the content
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_save_shipping_address);
            dialog.setCancelable(true);

            TextView tvAddress = dialog.findViewById(R.id.tv_address);
            String fullAddress = number + ", " + street + ", " + district + ", " + city + ", " + country;
            tvAddress.setText(fullAddress);

            // Set up the close button (X)
            ImageView imgClose = dialog.findViewById(R.id.img_close);
            imgClose.setOnClickListener(v -> dialog.dismiss());  // Close the dialog when the close button is clicked

            // Show the dialog
            dialog.show();
        }
    }
}
