package com.example.chatapp.Data;

import android.graphics.drawable.Drawable;

public class ChatlistItem {

    private Drawable icon;
    private String userName;
    private String message;
    public ChatlistItem() {}
    public ChatlistItem(String userName, String message) {
        this.userName = userName;
        this.message = message;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public String getMessage() {
        return message;
    }
    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}