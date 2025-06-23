package utils;

import com.example.greenerieplantie.R;

import java.util.ArrayList;

import models.Notifications;

public class ListNotifications {
    public static ArrayList<Notifications> getNotificationList() {
        ArrayList<Notifications> notificationList = new ArrayList<>();


        notificationList.add(new Notifications("You received a voucher!",
                "You have received a special voucher for your next purchase. Check it out now!",
                "3:15 PM", R.mipmap.ic_noti_voucher));


        notificationList.add(new Notifications("Watering reminder!",
                "It's time to water your plants. Don't forget to keep them hydrated!",
                "9:00 AM", R.mipmap.ic_noti_calender));


        notificationList.add(new Notifications("You received a voucher!",
                "You have received a special voucher for your next purchase. Check it out now!",
                "1:30 PM", R.mipmap.ic_noti_voucher));

        return notificationList;
    }
}
