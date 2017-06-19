package com.example.max.googlebooks;

import com.example.max.googlebooks.data.Util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UtilUnitTest {

    @Test
    public void formatDate() throws Exception {
        assertEquals(null, Util.formatDate(null));
        assertEquals("2000", Util.formatDate("2000"));
        assertEquals("2000-07", Util.formatDate("2000-07"));
        assertEquals("Jan 1, 2000", Util.formatDate("2000-01-01"));
        assertEquals("abc", Util.formatDate("abc"));
    }
}