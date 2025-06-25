package adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.greenerieplantie.NewsDetailActivity;
import com.example.greenerieplantie.R;

import java.util.List;

import models.NewsDetail;

public class NewsDetailAdapter extends RecyclerView.Adapter<NewsDetailAdapter.ViewHolder> {

    private List<NewsDetail> newsList;
    private Context context;

    public NewsDetailAdapter(List<NewsDetail> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsDetail news = newsList.get(position);
        holder.title.setText(news.getTitle());
        holder.authorDate.setText(news.getAuthor() + " • " + news.getDate());

        // Lấy tên ảnh (vd: img_blog1_01) và ánh xạ sang R.drawable.img_blog1_01
        Glide.with(context)
                .load(news.getImage())
                .placeholder(R.drawable.ic_launcher_background) // ảnh loading tạm
                .error(R.drawable.ic_launcher_foreground)      // nếu URL lỗi
                .into(holder.image);


        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, NewsDetailActivity.class);
            intent.putExtra("newsDetail", news);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, authorDate;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.newsTitle);
            authorDate = itemView.findViewById(R.id.newsMeta);
            image = itemView.findViewById(R.id.newsImage);
        }
    }
}
