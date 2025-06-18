package models;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderItem implements Parcelable {

    private String productName;
    private int imageResId;
    private String price;

    public OrderItem(String productName, int imageResId, String price) {
        this.productName = productName;
        this.imageResId = imageResId;
        this.price = price;
    }

    protected OrderItem(Parcel in) {
        productName = in.readString();
        imageResId = in.readInt();
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

    public int getImageResId() {
        return imageResId;
    }

    public String getPrice() {
        return price;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(productName);
        parcel.writeInt(imageResId);
        parcel.writeString(price);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
