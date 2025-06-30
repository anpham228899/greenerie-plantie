package connectors;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import models.NewsDetail;

public class NewsConnector {

    private static final String TAG = "NewsConnector";
    private static final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("newsDetails");

    public static void uploadNewsList(List<NewsDetail> list) {
        for (NewsDetail detail : list) {
            String slug = detail.getTitle().toLowerCase().replaceAll("[^a-z0-9]+", "_");

            ref.child(slug).setValue(detail)
                    .addOnSuccessListener(unused -> Log.d(TAG, "Upload success: " + slug))
                    .addOnFailureListener(e -> Log.e(TAG, "Upload error: " + e.getMessage()));
        }
    }
}
