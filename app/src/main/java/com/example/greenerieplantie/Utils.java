package com.example.greenerieplantie;

import android.content.Context;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

public class Utils {
    public static float cosineSimilarity(List<Float> a, List<Float> b) {
        float dot = 0f, normA = 0f, normB = 0f;
        int len = Math.min(a.size(), b.size());
        for (int i = 0; i < len; i++) {
            dot += a.get(i) * b.get(i);
            normA += a.get(i) * a.get(i);
            normB += b.get(i) * b.get(i);
        }
        return (float)(dot / (Math.sqrt(normA) * Math.sqrt(normB) + 1e-10));
    }


    public static MappedByteBuffer loadModelFile(Context context, String modelName) throws IOException {
        FileInputStream inputStream = context.getAssets().openFd(modelName).createInputStream();
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = context.getAssets().openFd(modelName).getStartOffset();
        long declaredLength = context.getAssets().openFd(modelName).getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
}
