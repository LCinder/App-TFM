package com.example.tfm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tweets implements Serializable {
    public List<Tweet> tweets;

    public Tweets() {
        this.tweets = new ArrayList<>();
    }

    public void push(Tweet tweet) {
        this.tweets.add(tweet);
    }

    public Tweet get(Integer position) {
        return this.tweets.get(position);
    }

    public Integer size() {
        return this.tweets.size();
    }
}
