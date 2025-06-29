package connectors;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Product;

public class ProductConnector {

    private final DatabaseReference productRef;

    public interface ProductCallback {
        void onProductsLoaded(List<Product> productList);
        void onError(DatabaseError error);
    }

    public ProductConnector() {
        productRef = FirebaseDatabase.getInstance().getReference("products");
    }

    public void getAllProducts(ProductCallback callback) {
        productRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                List<Product> productList = new ArrayList<>();
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    try {
                        // Bắt buộc: convert mọi kiểu về String an toàn
                        String id = String.valueOf(snapshot.child("product_id").getValue());
                        String name = String.valueOf(snapshot.child("product_name").getValue());
                        String category = String.valueOf(snapshot.child("category_id").getValue());
                        String description = String.valueOf(snapshot.child("product_description").getValue());
                        String instruction = String.valueOf(snapshot.child("product_instruction").getValue());
                        String level = String.valueOf(snapshot.child("product_level").getValue());
                        String water = String.valueOf(snapshot.child("water_demand").getValue());
                        String cond = String.valueOf(snapshot.child("conditions").getValue());
                        String rating = String.valueOf(snapshot.child("product_rating").getValue());
// Load tiếng Việt nếu có
                        String name_vi = String.valueOf(snapshot.child("product_name_vi").getValue());
                        String description_vi = String.valueOf(snapshot.child("product_description_vi").getValue());
                        String instruction_vi = String.valueOf(snapshot.child("product_instruction_vi").getValue());
                        String level_vi = String.valueOf(snapshot.child("product_level_vi").getValue());
                        String water_vi = String.valueOf(snapshot.child("water_demand_vi").getValue());
                        String cond_vi = String.valueOf(snapshot.child("conditions_vi").getValue());

                        // Parse số an toàn
                        double price = 0.0;
                        try {
                            Object rawPrice = snapshot.child("product_price").getValue();
                            price = rawPrice instanceof Number
                                    ? ((Number) rawPrice).doubleValue()
                                    : Double.parseDouble(String.valueOf(rawPrice));
                        } catch (Exception e) {
                            Log.e("ProductConnector", "Invalid product_price: " + e.getMessage());
                        }

                        double prevPrice = 0.0;
                        try {
                            Object rawPrev = snapshot.child("product_previous_price").getValue();
                            prevPrice = rawPrev instanceof Number
                                    ? ((Number) rawPrev).doubleValue()
                                    : Double.parseDouble(String.valueOf(rawPrev));
                        } catch (Exception e) {
                            Log.e("ProductConnector", "Invalid previous price: " + e.getMessage());
                        }

                        int discount = 0;
                        try {
                            Object rawDiscount = snapshot.child("product_discount").getValue();
                            discount = rawDiscount instanceof Number
                                    ? ((Number) rawDiscount).intValue()
                                    : Integer.parseInt(String.valueOf(rawDiscount));
                        } catch (Exception e) {
                            Log.e("ProductConnector", "Invalid discount: " + e.getMessage());
                        }

                        int stock = 0;
                        try {
                            Object rawStock = snapshot.child("product_stock").getValue();
                            stock = rawStock instanceof Number
                                    ? ((Number) rawStock).intValue()
                                    : Integer.parseInt(String.valueOf(rawStock));
                        } catch (Exception e) {
                            Log.e("ProductConnector", "Invalid stock: " + e.getMessage());
                        }

                        // Parse map: product_reviews
                        Map<String, String> reviews = new HashMap<>();
                        for (DataSnapshot reviewSnap : snapshot.child("product_reviews").getChildren()) {
                            reviews.put(reviewSnap.getKey(), String.valueOf(reviewSnap.getValue()));
                        }

                        // Parse map: product_images
                        Map<String, String> images = new HashMap<>();
                        for (DataSnapshot imgSnap : snapshot.child("product_images").getChildren()) {
                            images.put(imgSnap.getKey(), String.valueOf(imgSnap.getValue()));
                        }

                        // Tạo đối tượng Product
                        Product product = new Product(
                                id, name, category, description, instruction,
                                price, prevPrice, discount, stock,
                                level, water, cond, rating, reviews, images
                        );
                        product.setProduct_name_vi(name_vi);
                        product.setProduct_description_vi(description_vi);
                        product.setProduct_instruction_vi(instruction_vi);
                        product.setProduct_level_vi(level_vi);
                        product.setWater_demand_vi(water_vi);
                        product.setConditions_vi(cond_vi);
                        productList.add(product);
                    } catch (Exception e) {
                        Log.e("ProductConnector", "Parse error: " + e.getMessage());
                    }
                }

                Log.d("ProductConnector", "Loaded valid products: " + productList.size());
                callback.onProductsLoaded(productList);
            } else {
                callback.onError(DatabaseError.fromException(task.getException()));
            }
        });
    }

}
