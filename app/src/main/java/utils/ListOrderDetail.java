package utils;

import java.io.Serializable;
import java.util.List;

import models.OrderItem;

public class ListOrderDetail implements Serializable {
    private String orderNumber;
    private String orderDate;
    private List<OrderItem> orderItems;
    private String subtotal;
    private String shippingFee;
    private String discount;
    private int deliveryStage; // New attribute for the delivery stage (1 to 4)

    public ListOrderDetail(String orderNumber, String orderDate, List<OrderItem> orderItems,
                           String subtotal, String shippingFee, String discount, int deliveryStage) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.orderItems = orderItems;
        this.subtotal = subtotal;
        this.shippingFee = shippingFee;
        this.discount = discount;
        this.deliveryStage = deliveryStage;  // Initialize delivery stage
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

    public String getSubtotal() {
        return subtotal;
    }

    public String getShippingFee() {
        return shippingFee;
    }

    public String getDiscount() {
        return discount;
    }

    public int getDeliveryStage() {
        return deliveryStage;
    }

    public String getTotal() {
        int subtotalInt = Integer.parseInt(subtotal.replace(",", "").replace(" VND", ""));
        int shippingFeeInt = Integer.parseInt(shippingFee.replace(",", "").replace(" VND", ""));
        int discountInt = Integer.parseInt(discount.replace(",", "").replace(" VND", ""));

        int total = subtotalInt + shippingFeeInt - discountInt;
        return total + " VND";
    }
}
