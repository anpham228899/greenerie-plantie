package com.example.greenerieplantie;

import android.net.Uri;

public class ChatMessage {
    private String text;
    private boolean isUserMessage;
    private boolean isImage;
    private Uri imageUri;
    private String avatarUrl;
    private String userName;

    public ChatMessage(String text, boolean isUserMessage) {
        this.text = text;
        this.isUserMessage = isUserMessage;
        this.isImage = false;
        this.imageUri = null;
    }

    public ChatMessage(Uri imageUri) {
        this.text = null;
        this.isUserMessage = true;
        this.isImage = true;
        this.imageUri = imageUri;
    }

    public ChatMessage(String text, boolean isUserMessage, String avatarUrl, String userName) {
        this.text = text;
        this.isUserMessage = isUserMessage;
        this.isImage = false;
        this.imageUri = null;
        this.avatarUrl = avatarUrl;
        this.userName = userName;
    }

    public String getText() {
        return text;
    }

    public boolean isUserMessage() {
        return isUserMessage;
    }

    public boolean isImage() {
        return isImage;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
