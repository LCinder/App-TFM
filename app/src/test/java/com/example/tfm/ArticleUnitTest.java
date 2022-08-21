package com.example.tfm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.github.mikephil.charting.data.Entry;

import org.junit.Test;

import java.util.List;

public class ArticleUnitTest {
    @Test
    public void article_isCorrect(Tweet tweet) {
        assertTrue(String.class.isInstance(tweet.getTitle()));
        assertTrue(String.class.isInstance(tweet.getBody()));
        assertTrue(String.class.isInstance(tweet.getText()));
        assertTrue(String.class.isInstance(tweet.getDomain()));
        assertTrue(String.class.isInstance(tweet.getDate()));
        assertTrue(Double.class.isInstance(tweet.getFake()));
        assertTrue(String.class.isInstance(tweet.getInteractions()));
        assertTrue(String.class.isInstance(tweet.getImage()));

        assertTrue(tweet.getFake() >= 0 && tweet.getFake() <= 1);
        assertTrue(Integer.valueOf(tweet.getInteractions()) >= 0);
        assertTrue(tweet.getDate() != null);
        assertTrue(tweet.getDomain() != null);
    }
}
