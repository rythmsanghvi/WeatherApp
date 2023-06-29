package com.example.weatherapp;

public class Helpline {
    private String title;
    private String number;

    public Helpline() {
    }

    public Helpline(String title, String number) {
        this.title = title;
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
