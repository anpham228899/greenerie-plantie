package models;

public class Feedback {
    private int userImageResId;
    private String authorName;
    private int rating;
    private String content;
    private String date;

    public Feedback(int userImageResId, String authorName, int rating, String content, String date) {
        this.userImageResId = userImageResId;
        this.authorName = authorName;
        this.rating = rating;
        this.content = content;
        this.date = date;
    }

    public int getUserImageResId() {
        return userImageResId;
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