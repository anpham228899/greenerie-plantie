package adapters;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.greenerieplantie.CartActivity;
import com.example.greenerieplantie.R;
import models.Cart;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Cart> cartItemList;

    public CartAdapter(List<Cart> cartItemList) {
        this.cartItemList = cartItemList;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for each cart item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        // Get the cart item at the current position
        Cart cartItem = cartItemList.get(position);

        // Change background color depending on whether the item is selected or not
        if (cartItem.isSelected()) {
            // Set background to green if selected
            holder.itemView.setBackgroundResource(R.drawable.bg_edit_text_green);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.bg_edit_text);
        }

        // Bind data to the views
        holder.productName.setText(cartItem.getProductName());
        holder.productDescription.setText(cartItem.getProductDescription());
        holder.priceBeforeDiscount.setText(cartItem.getPriceBeforeDiscount());
        holder.priceAfterDiscount.setText(cartItem.getPriceAfterDiscount());
        holder.quantity.setText(String.valueOf(cartItem.getQuantity()));
        holder.productImage.setImageResource(cartItem.getImageResId());

        // Apply strikethrough to the price before discount
        holder.priceBeforeDiscount.setPaintFlags(holder.priceBeforeDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        // Handle quantity decrease and increase
        holder.icMinus.setOnClickListener(v -> updateQuantity(holder, cartItem, position, false));
        holder.icPlus.setOnClickListener(v -> updateQuantity(holder, cartItem, position, true));

        // Handle delete icon
        holder.deleteIcon.setOnClickListener(v -> {
            cartItemList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItemList.size());  // Ensure remaining items are updated
            // After removing item, refresh the cart item count
            ((CartActivity) v.getContext()).updateCartItemCount();
        });

        // Handle item selection toggle
        holder.itemView.setOnClickListener(v -> {
            cartItem.setSelected(!cartItem.isSelected());
            notifyItemChanged(position);
            ((CartActivity) v.getContext()).updateCartItemCount();
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    // Method to handle quantity change
    private void updateQuantity(CartViewHolder holder, Cart cartItem, int position, boolean isIncrease) {
        int newQuantity = cartItem.getQuantity() + (isIncrease ? 1 : -1);
        if (newQuantity >= 1) {
            cartItem.setQuantity(newQuantity);
            holder.quantity.setText(String.valueOf(cartItem.getQuantity()));
            notifyItemChanged(position);
            // After updating quantity, refresh the cart item count
            ((CartActivity) holder.itemView.getContext()).updateCartItemCount();
        }
    }

    // ViewHolder class to hold references to the views for each item in the cart
    public static class CartViewHolder extends RecyclerView.ViewHolder {

        private TextView productName, productDescription, priceBeforeDiscount, priceAfterDiscount, quantity;
        private ImageView productImage;
        private ImageButton icMinus, icPlus, deleteIcon;  // Use ImageButton for clickable buttons

        public CartViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productDescription = itemView.findViewById(R.id.product_description);
            priceBeforeDiscount = itemView.findViewById(R.id.price_before_discount);
            priceAfterDiscount = itemView.findViewById(R.id.price_after_discount);
            quantity = itemView.findViewById(R.id.quantity);
            productImage = itemView.findViewById(R.id.product_image);
            icMinus = itemView.findViewById(R.id.ic_minus);
            icPlus = itemView.findViewById(R.id.ic_plus);
            deleteIcon = itemView.findViewById(R.id.ic_delete);
        }
    }
}
