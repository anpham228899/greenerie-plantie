package connectors;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                callback.onError("Không thể tải đơn hàng: " + task.getException().getMessage());
            }
        });
    }

    private Order parseOrder(DataSnapshot snapshot) {
        Order order = new Order();

        // Các field chính
        order.orderId = snapshot.child("orderId").getValue(String.class);
        order.userId = snapshot.child("userId").getValue(String.class);
        order.status = snapshot.child("status").getValue(String.class);

        Long timestamp = snapshot.child("createdAt").getValue(Long.class);
        order.createdAt = (timestamp != null) ? timestamp : 0L;

        Double totalAmount = snapshot.child("totalAmount").getValue(Double.class);
        order.totalAmount = (totalAmount != null) ? totalAmount : 0.0;

        // ✅ Parse subtotal riêng nếu có
        Double subtotal = snapshot.child("subtotal").getValue(Double.class);
        order.subtotal = (subtotal != null) ? subtotal : 0.0;

        // ✅ Parse order_items
        Map<String, OrderItem> itemMap = new HashMap<>();
        DataSnapshot itemsSnap = snapshot.child("order_items");
        for (DataSnapshot itemSnap : itemsSnap.getChildren()) {
            OrderItem item = itemSnap.getValue(OrderItem.class);
            if (item != null) {
                itemMap.put(itemSnap.getKey(), item);
            }
        }
        order.setOrderItems(itemMap);

        // ✅ Parse shipping_info
        DataSnapshot shipSnap = snapshot.child("shipping_info");
        if (shipSnap.exists()) {
            ShippingInfo shippingInfo = new ShippingInfo();
            shippingInfo.name = shipSnap.child("name").getValue(String.class);
            shippingInfo.phone = shipSnap.child("phone").getValue(String.class);
            shippingInfo.email = shipSnap.child("email").getValue(String.class);
            shippingInfo.address = shipSnap.child("address").getValue(String.class);
            shippingInfo.ward = shipSnap.child("ward").getValue(String.class);
            shippingInfo.district = shipSnap.child("district").getValue(String.class);
            shippingInfo.province = shipSnap.child("province").getValue(String.class);
            shippingInfo.notes = shipSnap.child("notes").getValue(String.class);
            order.shippingInfo = shippingInfo;
        }

        // ✅ Parse payment_info nâng cấp
        DataSnapshot paymentSnap = snapshot.child("payment_info");
        if (paymentSnap.exists()) {
            PaymentInfo paymentInfo = new PaymentInfo();

            Double amount = paymentSnap.child("amount").getValue(Double.class);
            Double discount = paymentSnap.child("discount").getValue(Double.class);
            Double shippingFee = paymentSnap.child("shippingFee").getValue(Double.class);
            Double total = paymentSnap.child("total").getValue(Double.class);

            paymentInfo.amount = (amount != null) ? amount : 0.0;
            paymentInfo.discount = (discount != null) ? discount : 0.0;
            paymentInfo.shippingFee = (shippingFee != null) ? shippingFee : 0.0;
            paymentInfo.total = (total != null) ? total : 0.0;

            paymentInfo.method = paymentSnap.child("method").getValue(String.class);
            paymentInfo.status = paymentSnap.child("status").getValue(String.class);

            order.paymentInfo = paymentInfo;
        }

        return order;
    }



}
