package adapters;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.graphics.Paint;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenerieplantie.R;
import com.example.greenerieplantie.ProductDetailActivity;
import models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.productName.setText(product.getName());
        holder.tv_origin.setText(product.getOrigin());

        String category = product.getCategory();
        if (category != null && !category.isEmpty()) {
            setCategoryIcon(holder.imgCategoryIcon, category);
        } else {
            holder.imgCategoryIcon.setImageResource(R.mipmap.ic_launcher);
        }

        holder.tv_price.setText("VND " + product.getFormattedPrice());

        if (product.getDiscountPercentage() > 0) {
            holder.tv_original_price.setText("VND " + product.getFormattedOriginalPrice());
            holder.tv_original_price.setPaintFlags(holder.tv_original_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tv_original_price.setVisibility(View.VISIBLE);
        } else {
            holder.tv_original_price.setVisibility(View.GONE);
        }

        if (product.getImageResId() != 0) {
            holder.imgProduct.setImageResource(product.getImageResId());
        } else {
            holder.imgProduct.setImageResource(R.mipmap.ic_launcher);
        }

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("product_data", product);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView productName;
        TextView tv_origin_label;
        TextView tv_origin;
        TextView tv_category;
        ImageView imgCategoryIcon;
        TextView tv_price;
        TextView tv_original_price;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.img_product);
            productName = itemView.findViewById(R.id.tv_product_name);
            tv_origin_label = itemView.findViewById(R.id.tv_origin_label);
            tv_origin = itemView.findViewById(R.id.tv_origin);
            imgCategoryIcon = itemView.findViewById(R.id.img_category_icon);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_original_price = itemView.findViewById(R.id.tv_original_price);
        }
    }

    private void setCategoryIcon(ImageView imageView, String category) {
        int iconResId;
        switch (category) {
            case "Food Crops":
                iconResId = R.mipmap.ic_food_crops;
                break;
            case "Veggies":
                iconResId = R.mipmap.ic_veggies;
                break;
            case "Fruit Trees":
                iconResId = R.mipmap.ic_label_fruit_trees;
                break;
            case "Seed Pack":
                iconResId = R.mipmap.ic_label_seed_pack;
                break;
            case "Fertilizer":
                iconResId = R.mipmap.ic_label_fertilizer;
                break;
            case "Item Planting":
                iconResId = R.mipmap.ic_label_item_planting;
                break;
            default:
                iconResId = R.mipmap.ic_launcher;
                break;
        }
        imageView.setImageResource(iconResId);
    }

    public void updateProductList(List<Product> newList) {
        this.productList = newList;
        notifyDataSetChanged();
    }
}