package com.example.tfm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

public class Article extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article);

        TextView titleTweet = (TextView) findViewById(R.id.titleTweetArticle);
        TextView dateTweet = (TextView) findViewById(R.id.dateArticle);
        TextView bodyTweet = (TextView) findViewById(R.id.bodyTweetArticle);
        TextView classificationTweet = (TextView) findViewById(R.id.classificationTweetArticle);
        CardView banner = (CardView) findViewById(R.id.bannerArticle);
        String classification = "";

        Intent intent = getIntent();
        Tweet tweet = (Tweet) intent.getSerializableExtra("tweet");
        boolean fake = (tweet.getFake() < 0.5) ? false : true;

        if (!fake) {
            banner.setCardBackgroundColor(ContextCompat.getColor(banner.getContext(), R.color.real));
            classification = getResources().getString(R.string.real);
        }
        else {
            banner.setCardBackgroundColor(ContextCompat.getColor(banner.getContext(), R.color.fake));
            classification = getResources().getString(R.string.fake);
        }

        titleTweet.setText(tweet.getTitle());
        bodyTweet.setText(tweet.getBody());
        String date = tweet.getDate().contains("null") ? "" : "Fecha: " + tweet.getDate();
        dateTweet.setText(date + "\nFuente: " + tweet.getDomain());

        classificationTweet.setText(classification);
    }
}
