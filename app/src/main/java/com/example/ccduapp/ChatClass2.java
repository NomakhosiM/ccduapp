package com.example.ccduapp;

public class ChatClass2{
    private final String theText;
    private final int theTime;

    public ChatClass2(int theTime, String theText) {
        this.theTime = theTime;
        this.theText = theText;
    }

    public int getTheTime() {
        return theTime;
    }

    public String getTheText() {
        return theText;
    }
}
