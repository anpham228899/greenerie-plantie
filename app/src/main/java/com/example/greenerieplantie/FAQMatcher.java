package com.example.greenerieplantie;

import android.util.Log;

import java.util.List;

public class FAQMatcher {
    public static FAQItem findBest(List<FAQItem> faqList, List<List<Float>> embeds, List<Float> userVec, String tagFilter) {
        float bestSim = -1f;
        FAQItem bestItem = null;

        for (int i = 0; i < faqList.size(); i++) {
            if (tagFilter != null && !faqList.get(i).getTag().equalsIgnoreCase(tagFilter)) continue;

            float sim = Utils.cosineSimilarity(userVec, embeds.get(i));
            Log.d("FAQMatcher", "→ So sánh với: " + faqList.get(i).getQuestion());
            Log.d("FAQMatcher", "→ Độ tương đồng: " + sim);

            if (sim > bestSim) {
                bestSim = sim;
                bestItem = faqList.get(i);
            }
        }

        if (bestSim < 0.4f) {
            return new FAQItem("?", "\n" +
                    "39 / 5.000\n" +
                    "Sorry, I don't understand your question.", "", "");
        }

        Log.i("FAQMatcher", "Kết quả phù hợp nhất: " + bestItem.getQuestion() + " (similarity = " + bestSim + ")");
        return bestItem;
    }
    public static FAQItem findByTag(List<FAQItem> list, String tag) {
        for (FAQItem i : list) {
            if (i.getTag().equalsIgnoreCase(tag)) return i;
        }
        return new FAQItem("?", "\n" +
                "49 / 5.000\n" +
                "Sorry, I couldn't find a suitable answer.", "", "");
    }

}

