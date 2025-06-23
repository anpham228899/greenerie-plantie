package adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenerieplantie.OrderDetailActivity;
import com.example.greenerieplantie.R;

import java.util.ArrayList;
import java.util.List;

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

        holder.txtOrderNumber.setText(order.getOrderNumber());
        holder.txtOrderDate.setText(order.getOrderDate());
        holder.imgProductMain.setImageResource(order.getImageResId());

        holder.btnViewDetails.setOnClickListener(v -> {
            Intent intent = new Intent(context, OrderDetailActivity.class);
            intent.putExtra("order_number", order.getOrderNumber());
            intent.putExtra("order_date", order.getOrderDate());
            intent.putExtra("order_status", "shipping");
            intent.putParcelableArrayListExtra("order_items", (ArrayList<OrderItem>) order.getOrderItems());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProductMain;
        TextView txtOrderNumber, txtOrderDate;
        Button btnViewDetails;

        public OrderViewHolder(View itemView) {
            super(itemView);

            imgProductMain = itemView.findViewById(R.id.imgProductMain);
            txtOrderNumber = itemView.findViewById(R.id.txtProductPrice);
            txtOrderDate = itemView.findViewById(R.id.txtOrderDate);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
        }
    }
}
