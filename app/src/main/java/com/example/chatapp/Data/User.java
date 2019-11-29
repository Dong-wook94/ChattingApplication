package com.example.chatapp.Data;

import java.util.List;

public class User {
    private String userID;
    private String userName;
    private String deviceToken;
    private String phoneNumber;
    private List<String> friends;

    public User(String userName, String deviceToken, String phoneNumber) {
        this.userName = userName;
        this.deviceToken = deviceToken;
        this.phoneNumber = phoneNumber;
    }

    public User(String userID,String userName, String deviceToken, String phoneNumber) {
        this.userName = userName;
        this.userID = userID;
        this.deviceToken = deviceToken;
        this.phoneNumber = phoneNumber;
    }

    public User(String userID, String userName, String deviceToken, String phoneNumber, List<String> friends) {
        this.userID = userID;
        this.userName = userName;
        this.deviceToken = deviceToken;
        this.phoneNumber = phoneNumber;
        this.friends = friends;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
