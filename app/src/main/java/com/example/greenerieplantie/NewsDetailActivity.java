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

            int resId = getResources().getIdentifier(news.getFirstImageName(), "drawable", getPackageName());
            if (resId != 0) {
                headerImage.setImageResource(resId);
            }

            for (NewsBlock block : news.getBlocks()) {
                switch (block.getType()) {
                    case HEADING:
                        addHeading(block.getContent());
                        break;
                    case PARAGRAPH:
                        addParagraph(block.getContent());
                        break;
                    case IMAGE:
                        addImage(block.getContent());
                        break;
                }
            }
        } else {
            titleView.setText("No News Found");
        }
    }

    private void addHeading(String text) {
        TextView heading = new TextView(this);
        heading.setText(text);
        heading.setTextSize(18);
        heading.setTypeface(null, Typeface.BOLD);
        heading.setPadding(0, 24, 0, 12);
        blogContentContainer.addView(heading);
    }

    private void addParagraph(String text) {
        TextView paragraph = new TextView(this);
        paragraph.setText(text);
        paragraph.setTextSize(14);
        paragraph.setPadding(0, 0, 0, 16);
        blogContentContainer.addView(paragraph);
    }

    private void addImage(String imageName) {
        ImageView image = new ImageView(this);
        image.setAdjustViewBounds(true);
        int resId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        if (resId != 0) {
            image.setImageResource(resId);
            blogContentContainer.addView(image);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share && news != null) {
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
