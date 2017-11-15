package com.shypz.theasoft.shypz.model;

/**
 * Created by H216104 on 11/11/2017.
 */

public class User {


    private String username;
    private String userEmail;
    private String user_Password;
    private String user_Mobile;

    public User(String username, String userEmail, String user_Password, String user_Mobile) {
        this.username = username;
        this.userEmail = userEmail;
        this.user_Password = user_Password;
        this.user_Mobile = user_Mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUser_Password() {
        return user_Password;
    }

    public void setUser_Password(String user_Password) {
        this.user_Password = user_Password;
    }

    public String getUser_Mobile() {
        return user_Mobile;
    }

    public void setUser_Mobile(String user_Mobile) {
        this.user_Mobile = user_Mobile;
    }
}
