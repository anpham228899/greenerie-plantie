package models;

import java.io.Serializable;

public class NewsBlock implements Serializable {
    public enum Type { HEADING, PARAGRAPH, IMAGE }

    private String type; // đổi từ Type sang String
    private String content;

    public NewsBlock() {} // constructor rỗng

    public NewsBlock(Type type, String content) {
        this.type = type.name();  // lưu enum dưới dạng String
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

