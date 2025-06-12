package models;

import java.io.Serializable;
import java.util.List;

public class NewsDetail implements Serializable {

    private String title;
    private String author;
    private String date;
    private List<NewsBlock> blocks;
    private int imageResId;

    public NewsDetail(String title, String author, String date, List<NewsBlock> blocks, int imageResId) {
        this.title = title;
        this.author = author;
        this.date = date;
        this.blocks = blocks;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public List<NewsBlock> getBlocks() {
        return blocks;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setBlocks(List<NewsBlock> blocks) {
        this.blocks = blocks;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
    public String getFirstImageName() {
        for (NewsBlock block : blocks) {
            if (block.getType() == NewsBlock.Type.IMAGE) {
                return block.getContent();
            }
        }
        return null;
    }

}
