package com.example.tfm;

import java.io.Serializable;

public class Tweet implements Serializable {
    public String title;
    public String body;
    public String text;
    public String image;
    public String interactions;
    public double fake;
    private String date;
    private String domain;

    public Tweet(String title, String body, String text, String interactions, String image, double fake, String date, String domain) {
        this.title = title;
        this.body = body;
        this.text = text;
        this.interactions = interactions;
        this.image = image;
        this.fake = fake;
        this.date = date;
        this.domain = domain;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public double getFake() {
        return fake;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getInteractions() {
        return interactions;
    }

    public String getDate() {
        return date;
    }

    public String getDomain() {
        return domain;
    }
}
