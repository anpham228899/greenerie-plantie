package models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

public class Product implements Parcelable {
    private String productId; // Add this field
    private String name;
    private String origin;
    private String category;
    private double price;
    private int imageResId;
    private String description;
    private String level;
    private String waterNeeds;
    private String specialConditions;
    private String growthPeriod;
    private String sellingAmount;
    private double originalPrice;
    private double discountPercentage;

    public Product(String productId, String name, String origin, String category, double price, int imageResId) {
        this(productId, name, origin, category, price, imageResId,
                "No description provided.", "N/A", "N/A", "N/A", "N/A", "N/A", price, 0.0);
    }

    public Product(String productId, String name, String origin, String category, double price, int imageResId,
                   String description, String level, String waterNeeds, String specialConditions,
                   String growthPeriod, String sellingAmount, double originalPrice, double discountPercentage) {
        this.productId = productId;
        this.name = name;
        this.origin = origin;
        this.category = category;
        this.price = price;
        this.imageResId = imageResId;
        this.description = description;
        this.level = level;
        this.waterNeeds = waterNeeds;
        this.specialConditions = specialConditions;
        this.growthPeriod = growthPeriod;
        this.sellingAmount = sellingAmount;
        this.originalPrice = originalPrice;
        this.discountPercentage = discountPercentage;
    }
    public String getProductId() { return productId; }

    public String getName() { return name; }
    public String getOrigin() { return origin; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getImageResId() { return imageResId; }
    public String getDescription() { return description; }
    public String getLevel() { return level; }
    public String getWaterNeeds() { return waterNeeds; }
    public String getSpecialConditions() { return specialConditions; }
    public String getGrowthPeriod() { return growthPeriod; }
    public String getSellingAmount() { return sellingAmount; }
    public double getOriginalPrice() { return originalPrice; }
    public double getDiscountPercentage() { return discountPercentage; }

    public String getFormattedPrice() {
        return String.format(Locale.getDefault(), "%,.0f", price);
    }

    public String getFormattedOriginalPrice() {
        return String.format(Locale.getDefault(), "%,.0f", originalPrice);
    }

    public String getFormattedDiscountPercentage() {
        if (discountPercentage > 0) {
            return String.format(Locale.getDefault(), "%.0f%%", discountPercentage * 100);
        }
        return "";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productId);
        dest.writeString(this.name);
        dest.writeString(this.origin);
        dest.writeString(this.category);
        dest.writeDouble(this.price);
        dest.writeInt(this.imageResId);
        dest.writeString(this.description);
        dest.writeString(this.level);
        dest.writeString(this.waterNeeds);
        dest.writeString(this.specialConditions);
        dest.writeString(this.growthPeriod);
        dest.writeString(this.sellingAmount);
        dest.writeDouble(this.originalPrice);
        dest.writeDouble(this.discountPercentage);
    }

    protected Product(Parcel in) {
        this.productId = in.readString();
        this.name = in.readString();
        this.origin = in.readString();
        this.category = in.readString();
        this.price = in.readDouble();
        this.imageResId = in.readInt();
        this.description = in.readString();
        this.level = in.readString();
        this.waterNeeds = in.readString();
        this.specialConditions = in.readString();
        this.growthPeriod = in.readString();
        this.sellingAmount = in.readString();
        this.originalPrice = in.readDouble();
        this.discountPercentage = in.readDouble();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}