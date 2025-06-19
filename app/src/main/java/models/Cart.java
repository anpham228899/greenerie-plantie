package models;

import java.io.Serializable;

public class Cart implements Serializable {

    private String productName;
    private String productDescription;
    private String priceBeforeDiscount;
    private String priceAfterDiscount;
    private int quantity;
    private int imageResId;
    private boolean isSelected;  // Track if the item is selected

    public Cart(String productName, String productDescription, String priceBeforeDiscount, String priceAfterDiscount, int quantity, int imageResId) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.priceBeforeDiscount = priceBeforeDiscount;
        this.priceAfterDiscount = priceAfterDiscount;
        this.quantity = quantity;
        this.imageResId = imageResId;
        this.isSelected = false; // Default is not selected
    }

    // Getters and setters
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getPriceBeforeDiscount() {
        return priceBeforeDiscount;
    }

    public void setPriceBeforeDiscount(String priceBeforeDiscount) {
        this.priceBeforeDiscount = priceBeforeDiscount;
    }

    public String getPriceAfterDiscount() {
        return priceAfterDiscount;
    }

    public void setPriceAfterDiscount(String priceAfterDiscount) {
        this.priceAfterDiscount = priceAfterDiscount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
