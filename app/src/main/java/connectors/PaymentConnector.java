package connectors;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import models.PaymentInfo;

public class PaymentConnector {

    private static final DatabaseReference paymentRef =
            FirebaseDatabase.getInstance().getReference("payments");

    // Ghi payment vào Firebase (theo orderId)
    public static void savePayment(String orderId, PaymentInfo paymentInfo,
                                   Runnable onSuccess, Runnable onFailure) {
        paymentRef.child(orderId).setValue(paymentInfo)
                .addOnSuccessListener(unused -> {
                    if (onSuccess != null) onSuccess.run();
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    if (onFailure != null) onFailure.run();
                });
    }
    public static void loadPayment(String orderId, PaymentLoadCallback callback) {
        paymentRef.child(orderId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                PaymentInfo info = task.getResult().getValue(PaymentInfo.class);
                callback.onLoaded(info);
            } else {
                callback.onFailed(task.getException());
            }
        });
    }

    public interface PaymentLoadCallback {
        void onLoaded(PaymentInfo paymentInfo);
        void onFailed(Exception e);
    }

    // Bạn có thể thêm các phương thức như loadPayment(orderId), updatePayment(), deletePayment() nếu cần
}
