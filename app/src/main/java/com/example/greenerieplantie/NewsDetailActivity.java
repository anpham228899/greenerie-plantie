package com.example.greenerieplantie;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import models.NewsBlock;
import models.NewsDetail;

public class NewsDetailActivity extends AppCompatActivity {

    private LinearLayout blogContentContainer;
    private NewsDetail news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_news_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView backButton = findViewById(R.id.btnBack);
        ImageView headerImage = findViewById(R.id.headerImage);
        TextView titleView = findViewById(R.id.detailTitle);
        TextView authorDateView = findViewById(R.id.detailAuthorDate);
        blogContentContainer = findViewById(R.id.blogContentContainer);

        if (backButton != null) {
            backButton.setOnClickListener(v -> finish());
        }

        news = (NewsDetail) getIntent().getSerializableExtra("newsDetail");

        if (news != null) {
            titleView.setText(news.getTitle());
            authorDateView.setText(news.getAuthor() + " • " + news.getDate());

            int imageResId = getResources().getIdentifier(news.getImage(), "drawable", getPackageName());
            if (imageResId != 0) {
                headerImage.setImageResource(imageResId);
            }

            if (news.getBlocks() != null) {
                for (NewsBlock block : news.getBlocks()) {
                    String type = block.getType();
                    String content = block.getContent();
                    if (type == null || content == null) continue;

                    switch (type) {
                        case "HEADING":
                            addTextBlock(content, true);
                            break;
                        case "PARAGRAPH":
                            addTextBlock(content, false);
                            break;
                        case "IMAGE":
                            addImageBlock(content);
                            break;
                    }
                }
            }
        } else {
            titleView.setText("News not found");
        }
    }

    private void addTextBlock(String text, boolean isHeading) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(isHeading ? 18 : 14);
        textView.setTypeface(null, isHeading ? Typeface.BOLD : Typeface.NORMAL);
        textView.setPadding(0, isHeading ? 24 : 0, 0, 16);
        blogContentContainer.addView(textView);
    }

    private void addImageBlock(String imageUrl) {
        ImageView imageView = new ImageView(this);
        imageView.setAdjustViewBounds(true);
        imageView.setPadding(0, 16, 0, 16);
        blogContentContainer.addView(imageView);

        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (news == null) return super.onOptionsItemSelected(item);

        int id = item.getItemId();
        if (id == R.id.action_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, news.getTitle());
            shareIntent.putExtra(Intent.EXTRA_TEXT, news.getTitle() + "\nby " + news.getAuthor());
            startActivity(Intent.createChooser(shareIntent, "Share via"));
            return true;
        } else if (id == R.id.action_home) {
            startActivity(new Intent(this, HomepageActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
