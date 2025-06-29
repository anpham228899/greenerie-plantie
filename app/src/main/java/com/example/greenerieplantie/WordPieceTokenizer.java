package com.example.greenerieplantie;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WordPieceTokenizer {
    private final Map<String, Integer> vocab;
    private final int maxInputCharsPerWord = 100;
    private final String unknownToken = "[UNK]";

    public WordPieceTokenizer(Context context) throws Exception {
        vocab = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open("vocab.txt")));
        String line;
        int index = 0;
        while ((line = reader.readLine()) != null) {
            vocab.put(line, index++);
        }
        reader.close();
    }

    public int[] tokenize(String text) {
        List<Integer> result = new ArrayList<>();

        // Thêm token [CLS] đầu vào
        result.add(vocab.getOrDefault("[CLS]", 101));

        for (String token : whitespaceTokenize(text.toLowerCase(Locale.ROOT))) {
            if (token.length() > maxInputCharsPerWord) {
                result.add(vocab.getOrDefault(unknownToken, 100));
                continue;
            }

            int start = 0;
            List<Integer> subTokens = new ArrayList<>();
            boolean isBad = false;

            while (start < token.length()) {
                int end = token.length();
                String subStr = null;

                while (start < end) {
                    String substr = token.substring(start, end);
                    if (start > 0) substr = "##" + substr;
                    if (vocab.containsKey(substr)) {
                        subStr = substr;
                        break;
                    }
                    end--;
                }

                if (subStr == null || !vocab.containsKey(subStr)) {
                    isBad = true;
                    break;
                }

                subTokens.add(vocab.get(subStr));
                start = end;
            }

            if (isBad) {
                result.add(vocab.getOrDefault(unknownToken, 100));
            } else {
                result.addAll(subTokens);
            }
        }

        // Thêm token [SEP] cuối câu
        result.add(vocab.getOrDefault("[SEP]", 102));

        // Thay thế token ID vượt quá 30521 bằng [UNK]
        int maxTokenId = 30521;
        int unkId = vocab.getOrDefault(unknownToken, 100);
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i) > maxTokenId) {
                System.out.println("Token ID vượt quá giới hạn: " + result.get(i) + " → thay bằng [UNK]");
                result.set(i, unkId);
            }
        }

        // Giới hạn tối đa 512 tokens
        if (result.size() > 512) {
            result = result.subList(0, 512);
        }

        // In danh sách token ID để debug
        System.out.println("Token ID list: " + result);

        // Chuyển List<Integer> → int[]
        return result.stream().mapToInt(i -> i).toArray();
    }

    private List<String> whitespaceTokenize(String text) {
        return Arrays.asList(text.trim().split("\\s+"));
    }
}
