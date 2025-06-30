package models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;
import java.util.Map;

public class Product implements Parcelable {
    private String product_id;
    private String product_name;
    private String category_id;
    private String product_description;
    private String product_instruction;
    private double product_price;
    private double product_previous_price;
    private int product_discount;
    private int product_stock;
    private String product_level;
    private String water_demand;
    private String conditions;
    private String product_rating;
    private Map<String, String> product_reviews;
    private Map<String, String> product_images;
    private String product_name_vi;
    private String product_description_vi;
    private String product_instruction_vi;
    private String product_level_vi;

    public String getWater_demand_vi() {
        return water_demand_vi;
    }

    public void setWater_demand_vi(String water_demand_vi) {
        this.water_demand_vi = water_demand_vi;
    }

    public String getProduct_name_vi() {
        return product_name_vi;
    }

    public void setProduct_name_vi(String product_name_vi) {
        this.product_name_vi = product_name_vi;
    }

    public String getProduct_description_vi() {
        return product_description_vi;
    }

    public void setProduct_description_vi(String product_description_vi) {
        this.product_description_vi = product_description_vi;
    }

    public String getProduct_instruction_vi() {
        return product_instruction_vi;
    }

    public void setProduct_instruction_vi(String product_instruction_vi) {
        this.product_instruction_vi = product_instruction_vi;
    }

    public String getProduct_level_vi() {
        return product_level_vi;
    }

    public void setProduct_level_vi(String product_level_vi) {
        this.product_level_vi = product_level_vi;
    }

    public String getConditions_vi() {
        return conditions_vi;
    }

    public void setConditions_vi(String conditions_vi) {
        this.conditions_vi = conditions_vi;
    }

    private String water_demand_vi;
    private String conditions_vi;

    public Product() {
    }

    public Product(String product_id, String product_name, String category_id, String product_description, String product_instruction, double product_price, double product_previous_price, int product_discount, int product_stock, String product_level, String water_demand, String conditions, String product_rating, Map<String, String> product_reviews, Map<String, String> product_images) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.category_id = category_id;
        this.product_description = product_description;
        this.product_instruction = product_instruction;
        this.product_price = product_price;
        this.product_previous_price = product_previous_price;
        this.product_discount = product_discount;
        this.product_stock = product_stock;
        this.product_level = product_level;
        this.water_demand = water_demand;
        this.conditions = conditions;
        this.product_rating = product_rating;
        this.product_reviews = product_reviews;
        this.product_images = product_images;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }
    public String getLocalizedProductName(Context context) {
        return getLocalizedValue(context, product_name_vi, product_name);
    }

    public String getLocalizedProductDescription(Context context) {
        return getLocalizedValue(context, product_description_vi, product_description);
    }

    public String getLocalizedProductInstruction(Context context) {
        return getLocalizedValue(context, product_instruction_vi, product_instruction);
    }

    public String getLocalizedProductLevel(Context context) {
        return getLocalizedValue(context, product_level_vi, product_level);
    }

    public String getLocalizedWaterDemand(Context context) {
        return getLocalizedValue(context, water_demand_vi, water_demand);
    }

    public String getLocalizedConditions(Context context) {
        return getLocalizedValue(context, conditions_vi, conditions);
    }

    // Hàm tiện ích dùng chung
    private String getLocalizedValue(Context context, String vi, String en) {
        String lang = context.getSharedPreferences("LanguagePrefs", Context.MODE_PRIVATE)
                .getString("language", "en");
        if (lang.equals("vi") && vi != null && !vi.isEmpty()) {
            return vi;
        }
        return en;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }
    public String getFormattedPrice() {
        return String.format(Locale.getDefault(), "%,.0f", product_price);
    }

    public String getFormattedPreviousPrice() {
        return String.format(Locale.getDefault(), "%,.0f", product_previous_price);
    }
    protected Product(Parcel in) {
        product_id = in.readString();
        product_name = in.readString();
        category_id = in.readString();
        product_description = in.readString();
        product_instruction = in.readString();
        product_price = in.readDouble();
        product_previous_price = in.readDouble();
        product_discount = in.readInt();
        product_stock = in.readInt();
        product_level = in.readString();
        water_demand = in.readString();
        conditions = in.readString();
        product_rating = in.readString();
        product_reviews = in.readHashMap(String.class.getClassLoader());
        product_images = in.readHashMap(String.class.getClassLoader());
        product_name_vi = in.readString();
        product_description_vi = in.readString();
        product_instruction_vi = in.readString();
        product_level_vi = in.readString();
        water_demand_vi = in.readString();
        conditions_vi = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(product_id);
        dest.writeString(product_name);
        dest.writeString(category_id);
        dest.writeString(product_description);
        dest.writeString(product_instruction);
        dest.writeDouble(product_price);
        dest.writeDouble(product_previous_price);
        dest.writeInt(product_discount);
        dest.writeInt(product_stock);
        dest.writeString(product_level);
        dest.writeString(water_demand);
        dest.writeString(conditions);
        dest.writeString(product_rating);
        dest.writeMap(product_reviews);
        dest.writeMap(product_images);
        dest.writeString(product_name_vi);
        dest.writeString(product_description_vi);
        dest.writeString(product_instruction_vi);
        dest.writeString(product_level_vi);
        dest.writeString(water_demand_vi);
        dest.writeString(conditions_vi);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getFormattedDiscount() {
        return product_discount > 0 ? product_discount + "%" : "";
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_instruction() {
        return product_instruction;
    }

    public void setProduct_instruction(String product_instruction) {
        this.product_instruction = product_instruction;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public double getProduct_previous_price() {
        return product_previous_price;
    }

    public void setProduct_previous_price(double product_previous_price) {
        this.product_previous_price = product_previous_price;
    }

    public int getProduct_discount() {
        return product_discount;
    }

    public void setProduct_discount(int product_discount) {
        this.product_discount = product_discount;
    }

    public int getProduct_stock() {
        return product_stock;
    }

    public void setProduct_stock(int product_stock) {
        this.product_stock = product_stock;
    }

    public String getProduct_level() {
        return product_level;
    }

    public void setProduct_level(String product_level) {
        this.product_level = product_level;
    }

    public String getWater_demand() {
        return water_demand;
    }

    public void setWater_demand(String water_demand) {
        this.water_demand = water_demand;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getProduct_rating() {
        return product_rating;
    }

    public void setProduct_rating(String product_rating) {
        this.product_rating = product_rating;
    }

    public Map<String, String> getProduct_reviews() {
        return product_reviews;
    }

    public void setProduct_reviews(Map<String, String> product_reviews) {
        this.product_reviews = product_reviews;
    }

    public Map<String, String> getProduct_images() {
        return product_images;
    }

    public void setProduct_images(Map<String, String> product_images) {
        this.product_images = product_images;
    }
}