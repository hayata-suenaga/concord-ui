package com.example.concordui;

public class TempChat {
    String senderName;
    String text;

    public TempChat(String senderName, String text) {
        this.senderName = senderName;
        this.text = text;
    }

    @Override
    public String toString() { return senderName + ": " + text; }
}
