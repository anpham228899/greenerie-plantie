package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.greenerieplantie.R;
import models.OrderItem;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder> {

    private List<OrderItem> orderItems;

    public OrderItemAdapter(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public OrderItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail, parent, false);
        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderItemViewHolder holder, int position) {
        OrderItem orderItem = orderItems.get(position);

        holder.productName.setText(orderItem.getProductName());
        holder.productPrice.setText(orderItem.getPrice());
        holder.productImage.setImageResource(orderItem.getImageResId());
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public static class OrderItemViewHolder extends RecyclerView.ViewHolder {

        public TextView productName;
        public TextView productPrice;
        public ImageView productImage;

        public OrderItemViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.edtProductName);
            productPrice = itemView.findViewById(R.id.edtProductPrice);
            productImage = itemView.findViewById(R.id.imgProductImage);
        }
    }
}
