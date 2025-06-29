package com.example.greenerieplantie;

import android.content.Context;
import android.util.Log;

import org.tensorflow.lite.Interpreter;

import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextEmbeddingHelper {
    private final Interpreter tflite;
    private final WordPieceTokenizer tokenizer;

    // Cập nhật số chiều vector embedding để khớp với model thực tế
    private static final int EMBEDDING_DIM = 768;

    public TextEmbeddingHelper(Context context) throws Exception {
        // Load TFLite model
        MappedByteBuffer modelBuffer = Utils.loadModelFile(context, "use_lite_model.tflite");
        tflite = new Interpreter(modelBuffer);

        // Load tokenizer
        tokenizer = new WordPieceTokenizer(context);
    }

    public List<Float> embed(String text) {
        int[] inputIds = tokenizer.tokenize(text);
        int len = inputIds.length;

        int[][] inputIdsBatch = new int[1][len];
        int[][] inputMaskBatch = new int[1][len];
        int[][] inputTypeIdsBatch = new int[1][len];

        // Fill in mask and segment ids
        Arrays.fill(inputMaskBatch[0], 1);
        Arrays.fill(inputTypeIdsBatch[0], 0);
        inputIdsBatch[0] = inputIds;

        Object[] inputs = new Object[]{inputIdsBatch, inputMaskBatch, inputTypeIdsBatch};

        float[][][] output = new float[1][len][EMBEDDING_DIM];

        Map<Integer, Object> outputs = new HashMap<>();
        outputs.put(0, output);

        try {
            tflite.runForMultipleInputsOutputs(inputs, outputs);
        } catch (Exception e) {
            Log.e("TextEmbeddingHelper", "Error running TFLite model", e);
            return Collections.emptyList();
        }

        float[] pooled = new float[EMBEDDING_DIM];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < EMBEDDING_DIM; j++) {
                pooled[j] += output[0][i][j];
            }
        }
        for (int j = 0; j < EMBEDDING_DIM; j++) {
            pooled[j] /= len;
        }

        // Convert to List<Float>
        List<Float> result = new ArrayList<>();
        for (float v : pooled) result.add(v);

        Log.i("TextEmbeddingHelper", "Embedded vector created for: \"" + text + "\" → Length: " + result.size());
        return result;
    }
}
