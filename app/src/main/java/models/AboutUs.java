package models;

import java.io.Serializable;

public class AboutUs  implements Serializable {
    private String content;
    public AboutUs(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }
}
