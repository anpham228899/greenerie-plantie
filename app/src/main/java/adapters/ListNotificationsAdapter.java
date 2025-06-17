package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenerieplantie.R;

import java.util.List;

import models.Notifications;

public class ListNotificationsAdapter extends RecyclerView.Adapter<ListNotificationsAdapter.NotificationViewHolder> {

    private List<Notifications> notificationList;

    public ListNotificationsAdapter(List<Notifications> notificationList) {
        this.notificationList = notificationList;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_notifications, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        Notifications notification = notificationList.get(position);
        holder.title.setText(notification.getTitle());
        holder.message.setText(notification.getMessage());
        holder.timestamp.setText(notification.getTimestamp());
        holder.icon.setImageResource(notification.getIcon());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView title, message, timestamp;
        ImageView icon;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.notificationTitle);
            message = itemView.findViewById(R.id.notificationMessage);
            timestamp = itemView.findViewById(R.id.notificationTimestamp);
            icon = itemView.findViewById(R.id.notificationIcon);
        }
    }
}
