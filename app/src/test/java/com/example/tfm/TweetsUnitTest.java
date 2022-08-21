package com.example.tfm;

import org.junit.Test;

import static org.junit.Assert.*;

import com.github.mikephil.charting.data.Entry;

import java.util.List;

public class TweetsUnitTest {
    @Test
    public void dataElements_isCorrect(Tweets tweets) {
        assertFalse(tweets.size() == 0);
        assertTrue(tweets.size() >= 10);
        assertTrue(tweets.size() <= 100);
    }
}
