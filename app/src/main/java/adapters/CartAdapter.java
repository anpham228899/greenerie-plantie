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
import utils.ListCart;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Cart> cartItemList;

    public CartAdapter(List<Cart> cartItemList) {
        this.cartItemList = cartItemList;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        Cart cartItem = cartItemList.get(position);

        if (cartItem.isSelected()) {
            holder.itemView.setBackgroundResource(R.drawable.bg_edit_text_green);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.bg_edit_text);
        }

        holder.productName.setText(cartItem.getProductName());
        holder.productDescription.setText(cartItem.getProductType());
        holder.priceBeforeDiscount.setText(cartItem.getOriginalPrice());
        holder.priceAfterDiscount.setText(cartItem.getPriceAfterDiscount());
        holder.quantity.setText(String.valueOf(cartItem.getQuantity()));
        holder.productImage.setImageResource(cartItem.getImageResId());

        holder.priceBeforeDiscount.setPaintFlags(holder.priceBeforeDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.icMinus.setOnClickListener(v -> updateQuantity(holder, cartItem, position, false));
        holder.icPlus.setOnClickListener(v -> updateQuantity(holder, cartItem, position, true));

        holder.deleteIcon.setOnClickListener(v -> {
            ListCart.removeProduct(cartItem.getProductId());

            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItemList.size());

            if (v.getContext() instanceof CartActivity) {
                ((CartActivity) v.getContext()).refreshCartDisplay();
            }
        });

        holder.itemView.setOnClickListener(v -> {
            cartItem.setSelected(!cartItem.isSelected());
            notifyItemChanged(position);

            if (v.getContext() instanceof CartActivity) {
                ((CartActivity) v.getContext()).refreshCartDisplay();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    private void updateQuantity(CartViewHolder holder, Cart cartItem, int position, boolean isIncrease) {
        int newQuantity = cartItem.getQuantity() + (isIncrease ? 1 : -1);
        if (newQuantity >= 1) {
            cartItem.setQuantity(newQuantity);
            holder.quantity.setText(String.valueOf(cartItem.getQuantity()));

            notifyItemChanged(position);

            if (holder.itemView.getContext() instanceof CartActivity) {
                ((CartActivity) holder.itemView.getContext()).refreshCartDisplay();
            }
        }
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        private TextView productName, productDescription, priceBeforeDiscount, priceAfterDiscount, quantity;
        private ImageView productImage;
        private ImageButton icMinus, icPlus, deleteIcon;

        public CartViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.tv_product_name);
            productDescription = itemView.findViewById(R.id.tv_product_description);
            priceBeforeDiscount = itemView.findViewById(R.id.tv_price_before_discount);
            priceAfterDiscount = itemView.findViewById(R.id.tv_price_after_discount);
            quantity = itemView.findViewById(R.id.tv_quantity);
            productImage = itemView.findViewById(R.id.img_product);
            icMinus = itemView.findViewById(R.id.ic_minus);
            icPlus = itemView.findViewById(R.id.ic_plus);
            deleteIcon = itemView.findViewById(R.id.ic_delete);
        }
    }
}