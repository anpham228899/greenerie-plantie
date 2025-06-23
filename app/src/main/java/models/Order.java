package models;

import java.util.List;

public class Order {
    private String orderNumber;
    private String orderDate;
    private List<OrderItem> orderItems;
    private int imageResId;

    public Order(String orderNumber, String orderDate, List<OrderItem> orderItems, int imageResId) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.orderItems = orderItems;
        this.imageResId = imageResId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public int getImageResId() {
        return imageResId;
    }
}

