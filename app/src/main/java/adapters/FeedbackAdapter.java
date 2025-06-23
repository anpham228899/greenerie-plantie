package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenerieplantie.R;
import models.Feedback;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {

    private List<Feedback> feedbackList;
    private int displayLimit;

    public FeedbackAdapter(List<Feedback> feedbackList, int initialLimit) {
        this.feedbackList = feedbackList;
        this.displayLimit = initialLimit;
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback, parent, false);
        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        Feedback feedback = feedbackList.get(position);

        holder.userImage.setImageResource(feedback.getUserImageResId());
        holder.authorName.setText(feedback.getAuthorName());
        holder.content.setText(feedback.getContent());
        holder.date.setText(feedback.getDate());

        int rating = feedback.getRating();
        ImageView[] stars = {holder.star1, holder.star2, holder.star3, holder.star4, holder.star5};

        for (int i = 0; i < stars.length; i++) {
            if (i < rating) {
                stars[i].setImageResource(R.mipmap.ic_star_filled);
            } else {
                stars[i].setImageResource(R.mipmap.ic_star_empty);
            }
        }
    }

    @Override
    public int getItemCount() {
        return Math.min(feedbackList.size(), displayLimit);
    }

    public void setDisplayLimit(int limit) {
        this.displayLimit = limit;
        notifyDataSetChanged();
    }

    public void updateData(List<Feedback> newList, int newLimit) {
        this.feedbackList = newList;
        this.displayLimit = newLimit;
        notifyDataSetChanged();
    }

    public void addFeedback(Feedback feedback) {
        feedbackList.add(feedback);
        notifyDataSetChanged();
    }

    public static class FeedbackViewHolder extends RecyclerView.ViewHolder {
        ImageView userImage;
        TextView authorName;
        TextView content;
        TextView date;
        ImageView star1, star2, star3, star4, star5;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.feedback_user_image);
            authorName = itemView.findViewById(R.id.feedback_author_name);
            content = itemView.findViewById(R.id.feedback_content);
            date = itemView.findViewById(R.id.feedback_date);

            star1 = itemView.findViewById(R.id.feedback_star_1);
            star2 = itemView.findViewById(R.id.feedback_star_2);
            star3 = itemView.findViewById(R.id.feedback_star_3);
            star4 = itemView.findViewById(R.id.feedback_star_4);
            star5 = itemView.findViewById(R.id.feedback_star_5);
        }
    }
}