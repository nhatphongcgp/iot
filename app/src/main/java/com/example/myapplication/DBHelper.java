package com.example.myapplication;

public class DBHelper {
    private final String userName;
    private final String email;
    private final String number;
    public DBHelper(String userName, String email, String number) {
        this.userName = userName;
        this.email = email;
        this.number = number;
    }
    public String getUserName() {
        return userName;
    }
    public String getEmail() {
        return email;
    }
    public String getNumber() {
        return number;
    }
}