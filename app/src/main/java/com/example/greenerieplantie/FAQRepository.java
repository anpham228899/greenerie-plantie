package com.example.greenerieplantie;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class FAQRepository {
    private List<FAQItem> faqList;
    private List<List<Float>> faqEmbeds;

    public FAQRepository(Context context) throws Exception {
        AssetManager assetManager = context.getAssets();

        try (InputStream is1 = assetManager.open("faq_data.json");
             InputStreamReader reader1 = new InputStreamReader(is1)) {
            faqList = new Gson().fromJson(reader1, new TypeToken<List<FAQItem>>() {}.getType());
        }

        File embedFile = new File(context.getFilesDir(), "faq_embeds.json");
        if (!embedFile.exists()) {
            throw new Exception("Missing faq_embeds.json. Please generate it first.");
        }

        try (InputStream is2 = new FileInputStream(embedFile);
             InputStreamReader reader2 = new InputStreamReader(is2)) {
            faqEmbeds = new Gson().fromJson(reader2, new TypeToken<List<List<Float>>>() {}.getType());
        }

        if (faqList.size() != faqEmbeds.size()) {
            throw new Exception("FAQ size mismatch: " + faqList.size() + " vs " + faqEmbeds.size());
        }
    }

    public List<FAQItem> getFaqData() { return faqList; }
    public List<List<Float>> getFaqEmbeds() { return faqEmbeds; }
}