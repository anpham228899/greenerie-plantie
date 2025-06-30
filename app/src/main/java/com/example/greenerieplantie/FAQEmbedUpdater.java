package com.example.greenerieplantie;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;


public class FAQEmbedUpdater {
    public static void updateEmbeddings(Context context) {
        try {
            InputStream is = context.getAssets().open("faq_data.json");
            InputStreamReader reader = new InputStreamReader(is);
            List<FAQItem> faqData = new Gson().fromJson(reader, new TypeToken<List<FAQItem>>() {}.getType());

            TextEmbeddingHelper embedder = new TextEmbeddingHelper(context);
            JSONArray allEmbeds = new JSONArray();

            for (FAQItem item : faqData) {
                List<Float> vec = embedder.embed(item.getQuestion());
                JSONArray arr = new JSONArray();
                for (float v : vec) arr.put(v);
                allEmbeds.put(arr);
            }

            FileOutputStream fos = context.openFileOutput("faq_embeds.json", Context.MODE_PRIVATE);
            fos.write(allEmbeds.toString().getBytes());
            fos.close();

            Log.i("FAQEmbedUpdater", "Embeddings updated: " +
                    allEmbeds.length() + " vectors for " + faqData.size() + " questions");

        } catch (Exception e) {
            Log.e("FAQEmbedUpdater", "Lỗi ghi embeddings: " + e.getMessage(), e);
        }
    }
}
