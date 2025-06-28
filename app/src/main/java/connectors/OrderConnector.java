package connectors;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import models.Order;
import models.OrderItem;
import models.PaymentInfo;
import models.ShippingInfo;

public class OrderConnector {
    private final DatabaseReference ordersRef;

    public interface OrderCallback {
        void onOrdersLoaded(List<Order> orders);
        void onError(String errorMessage);
    }

    public OrderConnector() {
        ordersRef = FirebaseDatabase.getInstance().getReference("orders");
    }

    public void getAllOrders(OrderCallback callback) {
        ordersRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Order> orders = new ArrayList<>();
                for (DataSnapshot orderSnap : task.getResult().getChildren()) {
                    try {
                        Order order = parseOrder(orderSnap);
                        if (order != null) {
                            orders.add(order);
                        }
                    } catch (Exception e) {
                        Log.e("OrderConnector", "Parse error: " + e.getMessage());
                    }
                }
                callback.onOrdersLoaded(orders);
            } else {
                callback.onError("Failed to fetch orders: " + task.getException().getMessage());
            }
        });
    }
    public void getOrdersByUserId(String userId, OrderCallback callback) {
        DatabaseReference userOrdersRef = ordersRef.child(userId);

        userOrdersRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Order> orders = new ArrayList<>();
                for (DataSnapshot orderSnap : task.getResult().getChildren()) {
                    try {
                        Order order = orderSnap.getValue(Order.class);
                        if (order != null) {
                            orders.add(order);
                        }
                    } catch (Exception e) {
                        Log.e("OrderConnector", "Parse error: " + e.getMessage());
                    }
                }
                callback.onOrdersLoaded(orders);
            } else {
                callback.onError("Không thể tải đơn hàng: " + task.getException().getMessage());
            }
        });
    }

    private Order parseOrder(DataSnapshot snapshot) {
        Order order = new Order();

        order.orderId = String.valueOf(snapshot.child("order_id").getValue());
        order.userId = String.valueOf(snapshot.child("customer_id").getValue());
        order.status = snapshot.child("order_status").getValue(String.class);
        order.createdAt = snapshot.child("order_createdat").getValue(long.class);

        // FIX: Tránh null cho totalAmount
        Double total = snapshot.child("total_amount").getValue(Double.class);
        order.totalAmount = (total != null) ? total : 0.0;

        // Parse order_items
        List<OrderItem> itemList = new ArrayList<>();
        for (DataSnapshot itemSnap : snapshot.child("order_items").getChildren()) {
            OrderItem item = new OrderItem();

            String name = itemSnap.child("product_name").getValue(String.class);
            String price = String.valueOf(itemSnap.child("unit_price").getValue());
            String imageUrl = "";
            DataSnapshot imageSnap = itemSnap.child("product_images/image1");
            if (imageSnap.exists()) {
                imageUrl = imageSnap.getValue(String.class);
            }

            item.setImageUrl(imageUrl);
            item.setProductName(name);
            item.setPrice(price);

            itemList.add(item);
        }
        order.orderItems = itemList;

        // Parse shipping_info
        DataSnapshot shipSnap = snapshot.child("shipping_info");
        if (shipSnap.exists()) {
            ShippingInfo shippingInfo = new ShippingInfo();
            shippingInfo.name = shipSnap.child("ship_name").getValue(String.class);
            shippingInfo.phone = shipSnap.child("ship_phone").getValue(String.class);
            shippingInfo.email = shipSnap.child("ship_email").getValue(String.class);
            shippingInfo.address = shipSnap.child("ship_address").getValue(String.class);
            shippingInfo.ward = shipSnap.child("ship_ward").getValue(String.class);
            shippingInfo.district = shipSnap.child("ship_district").getValue(String.class);
            shippingInfo.province = shipSnap.child("ship_province").getValue(String.class);
            shippingInfo.notes = shipSnap.child("ship_additional_notes").getValue(String.class);
            order.shippingInfo = shippingInfo;
        }

        // Parse payment_info
        DataSnapshot paymentSnap = snapshot.child("payment_info");
        if (paymentSnap.exists()) {
            PaymentInfo paymentInfo = new PaymentInfo();
            Double payAmount = paymentSnap.child("amount").getValue(Double.class);
            paymentInfo.amount = (payAmount != null) ? payAmount : 0.0;
            paymentInfo.method = paymentSnap.child("method").getValue(String.class);
            paymentInfo.status = paymentSnap.child("status").getValue(String.class);
            order.paymentInfo = paymentInfo;
        }

        return order;
    }

}
