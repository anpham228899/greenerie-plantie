package models;

public class Product {
    private String name;
    private String origin;
    private String category;
    private String price;
    private int imageResId;

    public Product(String name, String origin, String category, String price, int imageResId) {
        this.name = name;
        this.origin = origin;
        this.category = category;
        this.price = price;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public String getOrigin() {
        return origin;
    }

    public String getCategory() {
        return category;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }
}
