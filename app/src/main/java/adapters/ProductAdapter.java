package adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.greenerieplantie.ProductDetailActivity;
import com.example.greenerieplantie.R;

import java.util.List;

import models.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    public void updateProductList(List<Product> newList) {
        this.productList = newList;
        notifyDataSetChanged();
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

        holder.productName.setText(product.getLocalizedProductName(holder.itemView.getContext()));
        String lang = holder.itemView.getContext()
                .getSharedPreferences("LanguagePrefs", Context.MODE_PRIVATE)
                .getString("language", "en");

        Log.d("LANG_CHECK", "Current lang: " + lang);
        Log.d("NAME_CHECK", "Hiển thị: " + product.getLocalizedProductName(holder.itemView.getContext()));

        holder.tv_origin.setText(product.getLocalizedProductInstruction(holder.itemView.getContext()));


        String category = product.getCategory_id();
        if (category != null && !category.isEmpty()) {
            setCategoryIcon(holder.imgCategoryIcon, category);
        } else {
            holder.imgCategoryIcon.setImageResource(R.mipmap.ic_launcher);
        }

        holder.tv_price.setText( String.format("%,.0f VND", product.getProduct_price()));

        if (product.getProduct_discount() > 0) {
            holder.tv_original_price.setText( String.format("%,.0f VND", product.getProduct_previous_price()));
            holder.tv_original_price.setPaintFlags(holder.tv_original_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tv_original_price.setVisibility(View.VISIBLE);
        } else {
            holder.tv_original_price.setVisibility(View.GONE);
        }

        // Load ảnh từ URL Firebase
        String imageUrl = product.getProduct_images() != null ? product.getProduct_images().get("image1") : null;
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.imgProduct);
        } else {
            holder.imgProduct.setImageResource(R.mipmap.ic_launcher);
        }

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("product_id", product.getProduct_id()); // ✅ chỉ truyền ID
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
        TextView tv_origin;
        ImageView imgCategoryIcon;
        TextView tv_price;
        TextView tv_original_price;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.img_product);
            productName = itemView.findViewById(R.id.tv_product_name);
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
            case "Fertilizer":
            case "Item Planting":
                iconResId = R.mipmap.ic_label_fruit_trees;
                break;
            default:
                iconResId = R.mipmap.ic_launcher;
                break;
        }
        imageView.setImageResource(iconResId);
    }
}
