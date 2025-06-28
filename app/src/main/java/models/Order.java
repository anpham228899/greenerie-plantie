package models;

import java.util.HashMap;
import java.util.Map;

public class Order {

    public String orderId;
    public String userId;
    public String status;
    public long createdAt;
    public double totalAmount;

    // ✅ Sửa từ List → Map cho đúng với Firebase
    public Map<String, OrderItem> orderItems = new HashMap<>();

    public ShippingInfo shippingInfo;
    public PaymentInfo paymentInfo;

    public Order() {} // Firebase cần constructor rỗng

    // ✅ Constructor chính xác
    public Order(String orderId, String userId, String status, long createdAt, double totalAmount,
                 Map<String, OrderItem> orderItems, ShippingInfo shippingInfo, PaymentInfo paymentInfo) {
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
        this.createdAt = createdAt;
        this.totalAmount = totalAmount;
        this.orderItems = orderItems;
        this.shippingInfo = shippingInfo;
        this.paymentInfo = paymentInfo;
    }

    // ✅ Getter và Setter đúng kiểu Map
    public Map<String, OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Map<String, OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ShippingInfo getShippingInfo() {
        return shippingInfo;
    }

    public void setShippingInfo(ShippingInfo shippingInfo) {
        this.shippingInfo = shippingInfo;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }
}
