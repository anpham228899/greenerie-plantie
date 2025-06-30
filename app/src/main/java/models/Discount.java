package models;

import java.io.Serializable;

public class Discount implements Serializable {
    private String code;
    private double discountPercentage;
    private boolean isUsed;

    // Constructor
    public Discount(String code, double discountPercentage) {
        this.code = code;
        this.discountPercentage = discountPercentage;
        this.isUsed = false; // Mới tạo mã giảm giá chưa sử dụng
    }

    // Getter and Setter
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }
}
