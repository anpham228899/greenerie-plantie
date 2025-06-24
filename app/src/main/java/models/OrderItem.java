package models;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderItem implements Parcelable {

    private String product_name;
    private String imageUrl;
    private String price;
    public OrderItem() {
    }
    public OrderItem(String productName, String imageResId, String price) {
        this.product_name = productName;
        this.imageUrl = imageResId;
        this.price = price;
    }

    protected OrderItem(Parcel in) {
        product_name = in.readString();
        imageUrl = in.readString();
        price = in.readString();
    }

    public static final Creator<OrderItem> CREATOR = new Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel in) {
            return new OrderItem(in);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };

    public String getProductName() {
        return product_name;
    }

    public String getImageResId() {
        return imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public String getPrice() {
        return price;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(product_name);
        parcel.writeString(imageUrl);
        parcel.writeString(price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void setProductName(String product_name) {
        this.product_name = product_name;
    }
}
