package com.example.greenerieplantie;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import org.tensorflow.lite.Interpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class MyImageClassifier {
    private static final int IMAGE_SIZE = 224;
    private final Interpreter interpreter;
    private final List<String> labels;
    private final Context context;
    private final TextEmbeddingHelper embedder;

    public static class ClassificationResult {
        public final String label;
        public final float confidence;

        public ClassificationResult(String label, float confidence) {
            this.label = label;
            this.confidence = confidence;
        }
    }

    public MyImageClassifier(Context context) throws IOException {
        this.context = context;

        // Load TFLite model từ assets
        MappedByteBuffer modelBuffer = Utils.loadModelFile(context, "cnn_model.tflite");
        interpreter = new Interpreter(modelBuffer);

        // Load danh sách nhãn
        labels = loadLabels("labels.txt");

        // Khởi tạo mô hình embedding
        TextEmbeddingHelper tempEmbedder;
        try {
            tempEmbedder = new TextEmbeddingHelper(context);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi khởi tạo TextEmbeddingHelper", e);
        }
        embedder = tempEmbedder;
    }

    public ClassificationResult classifyImage(Uri imageUri) throws IOException {
        Bitmap bitmap = uriToBitmap(imageUri);

        // Convert từ HARDWARE sang ARGB_8888 nếu cần
        if (bitmap.getConfig() == Bitmap.Config.HARDWARE || bitmap.getConfig() == null) {
            bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        }

        Bitmap resized = Bitmap.createScaledBitmap(bitmap, IMAGE_SIZE, IMAGE_SIZE, true);
        ByteBuffer input = preprocess(resized);

        float[][] output = new float[1][labels.size()];
        interpreter.run(input, output);

        int maxIndex = 0;
        float maxProb = 0f;
        for (int i = 0; i < labels.size(); i++) {
            if (output[0][i] > maxProb) {
                maxProb = output[0][i];
                maxIndex = i;
            }
        }

        String bestLabel = labels.get(maxIndex);
        return new ClassificationResult(bestLabel, maxProb);
    }

    // Các hàm phụ trợ:

    private ByteBuffer preprocess(Bitmap bitmap) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(4 * IMAGE_SIZE * IMAGE_SIZE * 3);
        buffer.order(ByteOrder.nativeOrder());

        for (int y = 0; y < IMAGE_SIZE; y++) {
            for (int x = 0; x < IMAGE_SIZE; x++) {
                int pixel = bitmap.getPixel(x, y);
                buffer.putFloat(((pixel >> 16) & 0xFF) / 255.0f); // R
                buffer.putFloat(((pixel >> 8) & 0xFF) / 255.0f);  // G
                buffer.putFloat((pixel & 0xFF) / 255.0f);         // B
            }
        }
        return buffer;
    }

    private List<String> loadLabels(String filename) throws IOException {
        List<String> labelList = new ArrayList<>();
        InputStream is = context.getAssets().open(filename);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = reader.readLine()) != null) {
            labelList.add(line.trim());
        }
        reader.close();
        return labelList;
    }

    private Bitmap uriToBitmap(Uri uri) throws IOException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.Source source = ImageDecoder.createSource(context.getContentResolver(), uri);
            return ImageDecoder.decodeBitmap(source);
        } else {
            return MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        }
    }
}
