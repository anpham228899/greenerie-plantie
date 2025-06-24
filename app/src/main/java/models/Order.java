package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {


    public String orderId;
    public String userId;
    public String status;
    public String createdAt;
    public double totalAmount;
    public List<OrderItem> orderItems = new ArrayList<>();

    public Order(String orderId, String userId, String status, String createdAt, double totalAmount, List<OrderItem> orderItems, ShippingInfo shippingInfo, PaymentInfo paymentInfo) {
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
        this.createdAt = createdAt;
        this.totalAmount = totalAmount;
        this.orderItems = orderItems;
        this.shippingInfo = shippingInfo;
        this.paymentInfo = paymentInfo;
    }



    public ShippingInfo shippingInfo;
    public PaymentInfo paymentInfo;


    public Order() {} // Firebase cần constructor rỗng
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
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

