package adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.greenerieplantie.OrderDetailActivity;
import com.example.greenerieplantie.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.Order;
import models.OrderItem;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;
    private Context context;

    public OrderAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        Log.d("DEBUG", "orderId = " + order.orderId);
        Log.d("DEBUG", "orderItems = " + order.orderItems);
        String shortOrderId = order.orderId;
        if (shortOrderId != null && shortOrderId.length() >= 6) {
            shortOrderId = shortOrderId.substring(shortOrderId.length() - 6); // Lấy 6 ký tự cuối
        }
        holder.txtOrderNumber.setText(shortOrderId);
        holder.txtOrderDate.setText("Ngày: " + formatDate(order.createdAt));

        // Gán mặc định nếu không có sản phẩm
        holder.txtProductName.setText("No product");

        if (order.orderItems != null && !order.orderItems.isEmpty()) {
            List<OrderItem> itemList = new ArrayList<>(order.orderItems.values());
            OrderItem item = itemList.get(0);

            Log.d("DEBUG", ">>> First item = " + item);
            Log.d("DEBUG", ">>> productName = " + item.getProductName());
            Log.d("DEBUG", ">>> imageResId = " + item.getImageResId());
            Log.d("DEBUG", ">>> imageUrl = " + item.getImageResId());

            if (item.getProductName() != null && !item.getProductName().isEmpty()) {
                holder.txtProductName.setText(item.getProductName());
            }

            // Gán ảnh nếu có
            String imageUrl = item.getImageResId();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Glide.with(context)
                        .load(imageUrl)
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(holder.imgProductMain);
            } else {
                holder.imgProductMain.setImageResource(R.drawable.ic_launcher_background);
            }
        } else {
            holder.imgProductMain.setImageResource(R.drawable.ic_launcher_background);
        }

        // Xử lý nút View Details
        holder.btnViewDetails.setOnClickListener(v -> {
            Intent intent = new Intent(context, OrderDetailActivity.class);
            intent.putExtra("orderId", order.orderId); // nếu bạn cần truyền nhiều hơn, có thể dùng Parcelable
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
    private String formatDate(long timestamp) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault());
        return sdf.format(new java.util.Date(timestamp));
    }
    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProductMain;
        TextView txtOrderNumber, txtOrderDate, txtProductName;
        Button btnViewDetails;

        public OrderViewHolder(View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            imgProductMain = itemView.findViewById(R.id.imgProductMain);
            txtOrderNumber = itemView.findViewById(R.id.txtProductPrice);
            txtOrderDate = itemView.findViewById(R.id.txtOrderDate);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
            txtProductName = itemView.findViewById(R.id.txtProductName);
        }
    }
}
