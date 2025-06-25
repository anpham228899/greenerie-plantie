package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.greenerieplantie.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Map;

import models.Cart;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final List<Cart> cartList;
    private final Context context;
    private final String userUid;

    public interface OnCartChangeListener {
        void onQuantityChanged();
        void onItemRemoved(Cart removedItem);
    }

    private final OnCartChangeListener cartChangeListener;

    public CartAdapter(List<Cart> cartList, Context context, String userUid, OnCartChangeListener listener) {
        this.cartList = cartList;
        this.context = context;
        this.userUid = userUid;
        this.cartChangeListener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart item = cartList.get(position);

        holder.tvProductName.setText(item.getProduct_name());
        holder.tvProductDescription.setText(item.getProduct_description());
        holder.tvPriceBeforeDiscount.setText(String.format("VND %,d", item.getProduct_previous_price()));
        holder.tvPriceAfterDiscount.setText(String.format("VND %,d", item.getProduct_price()));
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));

        // Load image (image1 from map)
        Map<String, String> imageMap = item.getProduct_images();
        if (imageMap != null && imageMap.containsKey("image1")) {
            Glide.with(context).load(imageMap.get("image1")).into(holder.imgProduct);
        }

        // Increase quantity
        holder.icPlus.setOnClickListener(v -> {
            int newQty = item.getQuantity() + 1;
            item.setQuantity(newQty);
            updateQuantityInFirebase(item.getProduct_id(), newQty);
            notifyItemChanged(position);
            if (cartChangeListener != null) cartChangeListener.onQuantityChanged();
        });

        // Decrease quantity
        holder.icMinus.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                int newQty = item.getQuantity() - 1;
                item.setQuantity(newQty);
                updateQuantityInFirebase(item.getProduct_id(), newQty);
                notifyItemChanged(position);
                if (cartChangeListener != null) cartChangeListener.onQuantityChanged();
            }
        });

        // Delete item
        holder.icDelete.setOnClickListener(v -> {
            removeItemFromFirebase(item.getProduct_id());
            Cart removedItem = cartList.remove(position);
            notifyItemRemoved(position);
            if (cartChangeListener != null) cartChangeListener.onItemRemoved(removedItem);
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    private void updateQuantityInFirebase(String productId, int quantity) {
        FirebaseDatabase.getInstance()
                .getReference("carts")
                .child(userUid)
                .child(productId)
                .child("quantity")
                .setValue(quantity);
    }

    private void removeItemFromFirebase(String productId) {
        FirebaseDatabase.getInstance()
                .getReference("carts")
                .child(userUid)
                .child(productId)
                .removeValue();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvProductName, tvProductDescription, tvPriceBeforeDiscount, tvPriceAfterDiscount, tvQuantity;
        ImageButton icPlus, icMinus, icDelete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.img_product);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvProductDescription = itemView.findViewById(R.id.tv_product_description);
            tvPriceBeforeDiscount = itemView.findViewById(R.id.tv_price_before_discount);
            tvPriceAfterDiscount = itemView.findViewById(R.id.tv_price_after_discount);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            icPlus = itemView.findViewById(R.id.ic_plus);
            icMinus = itemView.findViewById(R.id.ic_minus);
            icDelete = itemView.findViewById(R.id.ic_delete);
        }
    }
}
