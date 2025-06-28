package models;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderItem implements Parcelable {

    private String productName;
    private String imageResId;
    private String price;

    public OrderItem() {}

    public OrderItem(String productName, String imageResId, String price) {
        this.productName = productName;
        this.imageResId = imageResId;
        this.price = price;
    }

    protected OrderItem(Parcel in) {
        productName = in.readString();
        imageResId = in.readString();
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
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageResId() {
        return imageResId;
    }

    public void setImageResId(String imageResId) {
        this.imageResId = imageResId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(productName);
        parcel.writeString(imageResId);
        parcel.writeString(price);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
