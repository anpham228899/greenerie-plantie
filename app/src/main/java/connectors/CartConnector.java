package connectors;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import models.Cart;

public class CartConnector {

    private final DatabaseReference cartRef;

    public CartConnector(String userUid) {
        cartRef = FirebaseDatabase.getInstance().getReference("carts").child(userUid);
    }

    // 1. Thêm sản phẩm vào giỏ hàng
    public void addToCart(Cart cartItem) {
        cartRef.child(cartItem.getProduct_id()).setValue(cartItem);
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
    public void updateSelection(String productId, boolean isSelected) {
        cartRef.child(productId).child("isSelected").setValue(isSelected);
    }
    // Interface callback để xử lý kết quả lấy cart
    public interface CartLoadCallback {
        void onCartLoaded(List<Cart> cartItems);
        void onCartLoadFailed(Exception e);
    }
}
