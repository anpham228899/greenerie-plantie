package models;

import java.io.Serializable;
import java.util.Map;

public class Cart implements Serializable {

    private String product_id;
    private String category_id;
    private String product_name;
    private String product_description;
    private int product_discount;
    private int product_previous_price;
    private int product_price;
    private int product_stock;
    private Map<String, String> product_images; // chứa image1, image2, image3

    private int quantity;         // chỉ có trong Cart
    private boolean isSelected;   // chỉ có trong Cart

    public interface CartAddCallback {
        void onResult(boolean success);
    }
    public Cart() {
        // Constructor rỗng cho Firebase
    }

    public Cart(String product_id, String category_id, String product_name, String product_description,
                int product_discount, int product_previous_price, int product_price, int product_stock,
                Map<String, String> product_images, int quantity, boolean isSelected) {
        this.product_id = product_id;
        this.category_id = category_id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_discount = product_discount;
        this.product_previous_price = product_previous_price;
        this.product_price = product_price;
        this.product_stock = product_stock;
        this.product_images = product_images;
        this.quantity = quantity;
        this.isSelected = isSelected;
    }

    // Getter & Setter cho toàn bộ field

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public int getProduct_discount() {
        return product_discount;
    }

    public void setProduct_discount(int product_discount) {
        this.product_discount = product_discount;
    }

    public int getProduct_previous_price() {
        return product_previous_price;
    }

    public void setProduct_previous_price(int product_previous_price) {
        this.product_previous_price = product_previous_price;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public int getProduct_stock() {
        return product_stock;
    }

    public void setProduct_stock(int product_stock) {
        this.product_stock = product_stock;
    }

    public Map<String, String> getProduct_images() {
        return product_images;
    }

    public void setProduct_images(Map<String, String> product_images) {
        this.product_images = product_images;
    }
    public interface OnCartActionCallback {
        void onComplete(boolean success);
    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
