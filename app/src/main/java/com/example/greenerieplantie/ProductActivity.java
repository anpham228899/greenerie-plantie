package com.example.greenerieplantie;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import adapters.ProductAdapter;
import models.Product;
import utils.ListProduct;

public class ProductActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private List<Product> productList;
    private List<Product> fullProductList;
    private ProductAdapter productAdapter;
    private Spinner spinnerSort;
    private ImageButton backButton;
    private EditText etProductSearch;
    private ImageButton btnProductSearch;

    private Button btnAllPlants, btnFruitTree, btnFoodCrops, btnVeggies;

    private String currentCategoryFilter = "All Plants";
    private String currentSearchQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spinnerSort = findViewById(R.id.spinner_sort);
        recyclerView = findViewById(R.id.recycler_products);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        backButton = findViewById(R.id.btn_back);
        etProductSearch = findViewById(R.id.et_product_search);
        btnProductSearch = findViewById(R.id.btn_product_search);

        btnAllPlants = findViewById(R.id.btn_allplants);
        btnFruitTree = findViewById(R.id.btn_fruittree);
        btnFoodCrops = findViewById(R.id.btn_foodcrops);
        btnVeggies = findViewById(R.id.btn_veggies);


        backButton.setOnClickListener(v -> finish());

        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.sn_product_sort_options,
                android.R.layout.simple_spinner_item
        );
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSort.setAdapter(sortAdapter);
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Sorting by: " + selected, Toast.LENGTH_SHORT).show();
                applyFiltersAndSort();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        fullProductList = ListProduct.getSampleProductData();
        productList = new ArrayList<>(fullProductList);
        productAdapter = new ProductAdapter(productList);
        recyclerView.setAdapter(productAdapter);

        bottomNavigationView.setSelectedItemId(R.id.nav_product);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(this, HomepageActivity.class));
                return true;
            } else if (id == R.id.nav_product) {
                Toast.makeText(this, "You are already on the Product screen!", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.nav_chatbot) {
                startActivity(new Intent(this, ChatbotActivity.class));
                return true;
            } else if (id == R.id.nav_cart) {
                startActivity(new Intent(this, CartActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileManagementActivity.class));
                return true;
            }
            return false;
        });

        ImageButton cartButton = findViewById(R.id.imgbtn_cart);
        cartButton.setOnClickListener(v -> {
            startActivity(new Intent(this, CartActivity.class));
        });

        btnAllPlants.setOnClickListener(v -> {
            currentCategoryFilter = "All Plants";
            resetCategoryButtonColors();
            btnAllPlants.setTextColor(getResources().getColor(R.color.green_primary));
            btnAllPlants.setTextAppearance(R.style.TextStyleBold);
            applyFiltersAndSort();
        });

        btnFruitTree.setOnClickListener(v -> {
            currentCategoryFilter = "Fruit Trees";
            resetCategoryButtonColors();
            btnFruitTree.setTextColor(getResources().getColor(R.color.green_primary));
            btnFruitTree.setTextAppearance(R.style.TextStyleBold);
            applyFiltersAndSort();
        });

        btnFoodCrops.setOnClickListener(v -> {
            currentCategoryFilter = "Food Crops";
            resetCategoryButtonColors();
            btnFoodCrops.setTextColor(getResources().getColor(R.color.green_primary));
            btnFoodCrops.setTextAppearance(R.style.TextStyleBold);
            applyFiltersAndSort();
        });

        btnVeggies.setOnClickListener(v -> {
            currentCategoryFilter = "Veggies";
            resetCategoryButtonColors();
            btnVeggies.setTextColor(getResources().getColor(R.color.green_primary));
            btnVeggies.setTextAppearance(R.style.TextStyleBold);
            applyFiltersAndSort();
        });

        etProductSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentSearchQuery = s.toString();
                applyFiltersAndSort();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnProductSearch.setOnClickListener(v -> {
            Toast.makeText(this, "Searching for: " + currentSearchQuery, Toast.LENGTH_SHORT).show();
            applyFiltersAndSort();
        });

        btnAllPlants.setTextColor(getResources().getColor(R.color.green_primary));
        btnAllPlants.setTextAppearance(R.style.TextStyleBold);
    }

    private void resetCategoryButtonColors() {
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
        List<Product> filteredList = new ArrayList<>(fullProductList);

        if (!currentCategoryFilter.equals("All Plants")) {
            List<Product> tempFilteredList = new ArrayList<>();
            for (Product product : filteredList) {
                if (product.getCategory().equalsIgnoreCase(currentCategoryFilter)) {
                    tempFilteredList.add(product);
                }
            }
            filteredList = tempFilteredList;
        }

        if (!currentSearchQuery.isEmpty()) {
            List<Product> tempSearchFilteredList = new ArrayList<>();
            String query = currentSearchQuery.toLowerCase(Locale.getDefault());
            for (Product product : filteredList) {
                if (product.getName().toLowerCase(Locale.getDefault()).contains(query) ||
                        product.getOrigin().toLowerCase(Locale.getDefault()).contains(query) ||
                        product.getCategory().toLowerCase(Locale.getDefault()).contains(query)) {
                    tempSearchFilteredList.add(product);
                }
            }
            filteredList = tempSearchFilteredList;
        }

        String sortBy = spinnerSort.getSelectedItem().toString();
        switch (sortBy) {
            case "Name (A-Z)":
                Collections.sort(filteredList, Comparator.comparing(Product::getName));
                break;
            case "Name (Z-A)":
                Collections.sort(filteredList, (p1, p2) -> p2.getName().compareTo(p1.getName()));
                break;
            case "Price (Low to High)":
                Collections.sort(filteredList, Comparator.comparingDouble(Product::getPrice));
                break;
            case "Price (High to Low)":
                Collections.sort(filteredList, (p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()));
                break;
            default:
                break;
        }

        productList.clear();
        productList.addAll(filteredList);
        productAdapter.notifyDataSetChanged();
    }
}