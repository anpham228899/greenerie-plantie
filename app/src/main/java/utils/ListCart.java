package utils;

import com.example.greenerieplantie.R;

import java.util.ArrayList;
import java.util.List;
import models.Cart;

public class ListCart {

    public static List<Cart> getSampleCartData() {
        List<Cart> cartItems = new ArrayList<>();

        cartItems.add(new Cart(
                "ST25 Rice - Vietnam",
                "Seed Pack",
                "VND 200,000",
                "VND 180,000",
                1,  // initial quantity
                R.mipmap.img_aboutus1 // Change to your actual image resource
        ));

        cartItems.add(new Cart(
                "Mango Juice - Organic",
                "Fresh and Natural",
                "VND 100,000",
                "VND 90,000",
                1,  // initial quantity
                R.mipmap.img_aboutus1 // Change to your actual image resource
        ));

        cartItems.add(new Cart(
                "Mango Juice - Organic",
                "Fresh and Natural",
                "VND 100,000",
                "VND 90,000",
                1,  // initial quantity
                R.mipmap.img_aboutus1 // Change to your actual image resource
        ));

        cartItems.add(new Cart(
                "Mango Juice - Organic",
                "Fresh and Natural",
                "VND 100,000",
                "VND 90,000",
                1,  // initial quantity
                R.mipmap.img_aboutus1 // Change to your actual image resource
        ));

        cartItems.add(new Cart(
                "Mango Juice - Organic",
                "Fresh and Natural",
                "VND 100,000",
                "VND 90,000",
                1,  // initial quantity
                R.mipmap.img_aboutus1 // Change to your actual image resource
        ));

        return cartItems;
    }
}
