package models;

public class Feedback {
    private String authorName;
    private int rating;
    private String content;
    private String date;

    public Feedback(String authorName, int rating, String content, String date) {
        this.authorName = authorName;
        this.rating = rating;
        this.content = content;
        this.date = date;
    }

    public String getAuthorName() {
        return authorName;
    }

    public int getRating() {
        return rating;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }
}