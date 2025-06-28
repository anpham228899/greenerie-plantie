package utils;

import com.example.greenerieplantie.R; // Ensure this import is correct

import java.util.ArrayList;
import java.util.List;
import models.Cart;
import models.Product; // Import the Product model

public class ListCart {

//    private static List<Cart> cartItems = new ArrayList<>();
//
//    static {
//        if (cartItems.isEmpty()) {
//            cartItems.add(new Cart(
//                    "P005",
//                    "ST25 Rice - Vietnam",
//                    "Food Crops",
//                    "VND 200,000",
//                    "VND 180,000",
//                    1,
//                    R.drawable.img_blog2_01
//            ));
//
//            cartItems.add(new Cart(
//                    "P004",
//                    "Mango Juice",
//                    "Beverage",
//                    "VND 100,000",
//                    "VND 90,000",
//                    2,
//                    R.drawable.img_blog3_01
//            ));
//        }
//    }
//
//    public static List<Cart> getCartItems() {
//        return cartItems;
//    }
//
//    public static void addOrUpdateProduct(Product product, int quantity) {
//
//        boolean found = false;
//        for (Cart item : cartItems) {
//            if (item.getProductId() != null && item.getProductId().equals(product.getProductId())) {
//
//                item.setQuantity(item.getQuantity() + quantity);
//                found = true;
//                break;
//            }
//        }
//
//        if (!found) {
//            cartItems.add(new Cart(
//                    product.getProductId(),
//                    product.getName(),
//                    product.getCategory(),
//                    "VND " + product.getFormattedOriginalPrice(),
//                    "VND " + product.getFormattedPrice(),
//                    quantity,
//                    product.getImageResId()
//            ));
//        }
//    }
//    public static void removeProduct(String productId) {
//        cartItems.removeIf(item -> item.getProductId() != null && item.getProductId().equals(productId));
//    }
//
//    public static void clearCart() {
//        cartItems.clear();
//    }
}