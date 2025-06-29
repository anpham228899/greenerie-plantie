package utils;

import android.content.Context;
import android.content.SharedPreferences;

public class LangUtil {

    private static final String PREFS_NAME = "LanguagePrefs";
    private static final String KEY_LANGUAGE = "language";

    /**
     * Lấy mã ngôn ngữ hiện tại (ví dụ "vi", "en").
     */
    public static String getAppLanguage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_LANGUAGE, "en"); // Mặc định là tiếng Anh
    }

    /**
     * Trả về true nếu đang chọn tiếng Việt.
     */
    public static boolean isVietnamese(Context context) {
        return "vi".equals(getAppLanguage(context));
    }

    /**
     * Trả về true nếu đang chọn tiếng Anh.
     */
    public static boolean isEnglish(Context context) {
        return "en".equals(getAppLanguage(context));
    }
}
