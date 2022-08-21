package com.example.tfm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;

import org.junit.Test;

import java.util.List;

public class ChartUnitTest {
    @Test
    public void entries_isCorrect(List<Entry> entries) {
        assertFalse(entries.size() == 0);

        for(Entry entrie: entries)
            assertTrue(Integer.class.isInstance(entrie.getData()));

        assertTrue(entries.size() == 7);
    }

    public void pieEntries_isCorrect(List<PieEntry> pieEntries) {
        assertFalse(pieEntries.size() == 0);

        for(PieEntry entrie: pieEntries)
            assertTrue(Double.class.isInstance(entrie.getValue()));

        assertTrue(pieEntries.size() == 2);
    }
}