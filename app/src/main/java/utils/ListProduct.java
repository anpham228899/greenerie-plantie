package utils;

import com.example.greenerieplantie.R;

import java.util.ArrayList;
import java.util.List;

import models.Product;

public class ListProduct {

    public static List<Product> getSampleProductData() {
        List<Product> products = new ArrayList<>();

        products.add(new Product(
                "Red Apple",
                "USA",
                "Fruit Tree",
                "100,000 VND",
                R.drawable.ic_launcher_foreground
        ));

        products.add(new Product(
                "Carrot",
                "Vietnam",
                "Veggies",
                "30,000 VND",
                R.drawable.ic_launcher_foreground
        ));

        products.add(new Product(
                "Banana",
                "Thailand",
                "Fruit Tree",
                "50,000 VND",
                R.drawable.ic_launcher_foreground
        ));

        products.add(new Product(
                "Mango Juice",
                "Philippines",
                "Fruit Juice",
                "90,000 VND",
                R.drawable.ic_launcher_foreground
        ));

        products.add(new Product(
                "ST25 Rice",
                "Vietnam",
                "Grain",
                "180,000 VND",
                R.drawable.ic_launcher_foreground
        ));

        return products;
    }
}
