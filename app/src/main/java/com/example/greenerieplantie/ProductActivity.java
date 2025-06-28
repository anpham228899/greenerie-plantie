package com.example.greenerieplantie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import adapters.ProductAdapter;
import connectors.ProductConnector;
import models.Product;

public class ProductActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> fullProductList = new ArrayList<>();
    private List<Product> productList = new ArrayList<>();

    private Spinner spinnerSort;
    private EditText etProductSearch;
    private ImageButton btnProductSearch, backButton;
    private Button btnAllPlants, btnFruitTree, btnFoodCrops, btnVeggies;

    private String currentCategoryFilter = "All";
    private String currentSearchQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product);

        recyclerView = findViewById(R.id.recycler_products);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        productAdapter = new ProductAdapter(productList);
        recyclerView.setAdapter(productAdapter);

        spinnerSort = findViewById(R.id.spinner_sort);
        etProductSearch = findViewById(R.id.et_product_search);
        // Trì hoãn hiển thị bàn phím để tránh lỗi InputConnectionWrapper
        etProductSearch.postDelayed(() -> {
            etProductSearch.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(etProductSearch, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 200);

        btnProductSearch = findViewById(R.id.btn_product_search);
        backButton = findViewById(R.id.btn_back);
        btnAllPlants = findViewById(R.id.btn_allplants);
        btnFruitTree = findViewById(R.id.btn_fruittree);
        btnFoodCrops = findViewById(R.id.btn_foodcrops);
        btnVeggies = findViewById(R.id.btn_veggies);

        backButton.setOnClickListener(v -> finish());

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        NavMenuActivity.setupNavMenu(bottomNav, this, R.id.nav_product);
        bottomNav.setSelectedItemId(R.id.nav_product);

        findViewById(R.id.imgbtn_cart).setOnClickListener(v -> {
            startActivity(new Intent(this, CartActivity.class));
        });

        // Sort spinner
        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.sn_product_sort_options,
                android.R.layout.simple_spinner_item
        );
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSort.setAdapter(sortAdapter);
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyFiltersAndSort();
            }

            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Search
        etProductSearch.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentSearchQuery = s.toString();
                applyFiltersAndSort();
            }
            public void afterTextChanged(Editable s) {}
        });

        btnProductSearch.setOnClickListener(v -> {
            Toast.makeText(this, "Searching for: " + currentSearchQuery, Toast.LENGTH_SHORT).show();
            applyFiltersAndSort();
        });

        // Filter by category
        btnAllPlants.setOnClickListener(v -> setCategoryFilter("All", btnAllPlants));
        btnFruitTree.setOnClickListener(v -> setCategoryFilter("Fruit Trees", btnFruitTree));
        btnFoodCrops.setOnClickListener(v -> setCategoryFilter("Food Crops", btnFoodCrops));
        btnVeggies.setOnClickListener(v -> setCategoryFilter("Veggies", btnVeggies));

        setCategoryFilter("All", btnAllPlants); // default selected

        // Load from Firebase
        new ProductConnector().getAllProducts(new ProductConnector.ProductCallback() {
            @Override
            public void onProductsLoaded(List<Product> products) {
                fullProductList = products;
                applyFiltersAndSort();
            }

            @Override
            public void onError(DatabaseError error) {
                Toast.makeText(ProductActivity.this, "Failed to load products", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setCategoryFilter(String category, Button selectedButton) {
        currentCategoryFilter = category;
        resetCategoryButtonStyles();
        selectedButton.setTextColor(getResources().getColor(R.color.green_primary));
        selectedButton.setTextAppearance(R.style.TextStyleBold);
        applyFiltersAndSort();
    }

    private void resetCategoryButtonStyles() {
        int defaultColor = getResources().getColor(R.color.rating_unselected_color);
        btnAllPlants.setTextColor(defaultColor);
        btnFruitTree.setTextColor(defaultColor);
        btnFoodCrops.setTextColor(defaultColor);
        btnVeggies.setTextColor(defaultColor);

        btnAllPlants.setTextAppearance(R.style.TextStyleNormal);
        btnFruitTree.setTextAppearance(R.style.TextStyleNormal);
        btnFoodCrops.setTextAppearance(R.style.TextStyleNormal);
        btnVeggies.setTextAppearance(R.style.TextStyleNormal);
    }

    private void applyFiltersAndSort() {
        List<Product> filteredList = new ArrayList<>();

        for (Product product : fullProductList) {
            boolean matchesCategory = currentCategoryFilter.equals("All")
                    || product.getCategory_id().equalsIgnoreCase(currentCategoryFilter);

            boolean matchesSearch = currentSearchQuery.isEmpty()
                    || product.getProduct_name().toLowerCase(Locale.getDefault()).contains(currentSearchQuery.toLowerCase(Locale.getDefault()))
                    || (product.getCategory_id() != null && product.getCategory_id().toLowerCase(Locale.getDefault()).contains(currentSearchQuery.toLowerCase(Locale.getDefault())));

            if (matchesCategory && matchesSearch) {
                filteredList.add(product);
            }
        }

        String sortBy = spinnerSort.getSelectedItem().toString();
        switch (sortBy) {
            case "Name (A-Z)":
                Collections.sort(filteredList, Comparator.comparing(Product::getProduct_name));
                break;
            case "Name (Z-A)":
                Collections.sort(filteredList, (p1, p2) -> p2.getProduct_name().compareTo(p1.getProduct_name()));
                break;
            case "Price (Low to High)":
                Collections.sort(filteredList, Comparator.comparingDouble(Product::getProduct_price));
                break;
            case "Price (High to Low)":
                Collections.sort(filteredList, (p1, p2) -> Double.compare(p2.getProduct_price(), p1.getProduct_price()));
                break;
        }

        productList.clear();
        productList.addAll(filteredList);
        productAdapter.notifyDataSetChanged();
    }
}
