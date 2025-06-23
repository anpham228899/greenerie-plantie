package models;

import android.os.Parcel;
import android.os.Parcelable;

public class Cart implements Parcelable {
    private String productId;
    private String productName;
    private String productType;
    private String originalPrice;
    private String priceAfterDiscount;
    private int quantity;
    private int imageResId;
    private boolean isSelected;

    public Cart(String productId, String productName, String productType, String originalPrice, String priceAfterDiscount, int quantity, int imageResId) {
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.originalPrice = originalPrice;
        this.priceAfterDiscount = priceAfterDiscount;
        this.quantity = quantity;
        this.imageResId = imageResId;
        this.isSelected = false;
    }

    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getProductType() { return productType; }
    public String getOriginalPrice() { return originalPrice; }
    public String getPriceAfterDiscount() { return priceAfterDiscount; }
    public int getQuantity() { return quantity; }
    public int getImageResId() { return imageResId; }
    public boolean isSelected() { return isSelected; }

    // Setters (for quantity and selection status)
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setSelected(boolean selected) { isSelected = selected; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productId);
        dest.writeString(this.productName);
        dest.writeString(this.productType);
        dest.writeString(this.originalPrice);
        dest.writeString(this.priceAfterDiscount);
        dest.writeInt(this.quantity);
        dest.writeInt(this.imageResId);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    protected Cart(Parcel in) {
        this.productId = in.readString();
        this.productName = in.readString();
        this.productType = in.readString();
        this.originalPrice = in.readString();
        this.priceAfterDiscount = in.readString();
        this.quantity = in.readInt();
        this.imageResId = in.readInt();
        this.isSelected = in.readByte() != 0;
    }

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel source) {
            return new Cart(source);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };
}