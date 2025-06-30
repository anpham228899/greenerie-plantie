package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.greenerieplantie.R;
import models.AboutUs;
import java.util.ArrayList;

public class AboutUsAdapter extends RecyclerView.Adapter<AboutUsAdapter.AboutUsViewHolder> {
    private ArrayList<AboutUs> aboutUsList;
    
    public AboutUsAdapter(ArrayList<AboutUs> aboutUsList) {
        this.aboutUsList = aboutUsList;
    }

    @Override
    public AboutUsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_about_us, parent, false);
        return new AboutUsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AboutUsViewHolder holder, int position) {
        AboutUs aboutUs = aboutUsList.get(position);
        holder.contentTextView.setText(aboutUs.getContent());
    }

    @Override
    public int getItemCount() {
        return aboutUsList.size();
    }

    public static class AboutUsViewHolder extends RecyclerView.ViewHolder {
        TextView contentTextView;

        public AboutUsViewHolder(View itemView) {
            super(itemView);
            contentTextView = itemView.findViewById(R.id.tv_about_us_content_1);
        }
    }
}
