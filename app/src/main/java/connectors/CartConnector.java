package connectors;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import models.Cart;

public class CartConnector {

    private final String userId;
    private final DatabaseReference cartRef;

    public CartConnector(String userUid) {
        this.userId = userUid;
        this.cartRef = FirebaseDatabase.getInstance().getReference("carts").child(userUid);
    }

    // 1. Thêm sản phẩm vào giỏ hàng
    public interface OnCartActionCallback {
        void onComplete(boolean success);
    }

    public void addToCart(Cart cartItem, OnCartActionCallback callback) {
        if (userId == null || userId.trim().isEmpty()) {
            Log.e("CartConnector", "addToCart failed: userId is null or empty");
            callback.onComplete(false);
            return;
        }

        if (cartItem == null) {
            Log.e("CartConnector", "addToCart failed: cartItem is null");
            callback.onComplete(false);
            return;
        }

        String productId = cartItem.getProduct_id();
        if (productId == null || productId.trim().isEmpty()) {
            Log.e("CartConnector", "addToCart failed: product_id is null or empty");
            callback.onComplete(false);
            return;
        }

        cartRef.child(productId)
                .setValue(cartItem)
                .addOnSuccessListener(unused -> {
                    Log.d("CartConnector", "addToCart success: " + productId + " for user " + userId);
                    callback.onComplete(true);
                })
                .addOnFailureListener(e -> {
                    Log.e("CartConnector", "Firebase addToCart failed: " + e.getMessage());
                    callback.onComplete(false);
                });
    }

    // 2. Cập nhật số lượng
    public void updateQuantity(String productId, int newQuantity) {
        cartRef.child(productId).child("quantity").setValue(newQuantity);
    }

    // 3. Xoá sản phẩm khỏi giỏ hàng
    public void removeFromCart(String productId) {
        cartRef.child(productId).removeValue();
    }

    // 4. Xoá toàn bộ giỏ hàng của người dùng
    public void clearCart() {
        cartRef.removeValue();
    }

    // 5. Lấy toàn bộ giỏ hàng
    public void getCartItems(CartLoadCallback callback) {
        cartRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Cart> cartList = new ArrayList<>();
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    Cart item = snapshot.getValue(Cart.class);
                    if (item != null) {
                        cartList.add(item);
                    }
                }
                callback.onCartLoaded(cartList);
            } else {
                callback.onCartLoadFailed(task.getException());
            }
        });
    }

    // 6. Cập nhật chọn/bỏ chọn sản phẩm
    public void updateSelection(String productId, boolean isSelected) {
        cartRef.child(productId).child("isSelected").setValue(isSelected);
    }

    // Interface callback để xử lý kết quả lấy cart
    public interface CartLoadCallback {
        void onCartLoaded(List<Cart> cartItems);
        void onCartLoadFailed(Exception e);
    }
}
