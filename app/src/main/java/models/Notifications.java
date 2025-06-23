package models;

import java.io.Serializable;

public class Notifications implements Serializable {
    private String title;
    private String message;
    private String timestamp;
    private int icon;

    public Notifications(String title, String message, String timestamp, int icon) {
        this.title = title;
        this.message = message;
        this.timestamp = timestamp;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getIcon() {
        return icon;
    }
}
