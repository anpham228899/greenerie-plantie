package models;

import java.io.Serializable;

public class NewsBlock implements Serializable {
    public enum Type { HEADING, PARAGRAPH, IMAGE }

    private Type type;
    private String content;

    public NewsBlock(Type type, String content) {
        this.type = type;
        this.content = content;

    }

    public Type getType() { return type; }
    public String getContent() { return content; }
}
