package com.example.eventsapp;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
//    @Test
//    public void addition_isCorrect() {
//        assertEquals(4, 2 + 2);
//    }

    @Test
    public void removeTest() {
        Plug plug = new Plug();
        boolean result = plug.deleteRawData(111);
        assertEquals(true, result);
    }

    @Test
    public void confirmedTest() {
        Plug plug = new Plug();
        plug.getConfirmedData();
        int expectedCsize = 1;
        int expectedRsize = 16;
        int actualCsize = plug.confirmedDataSize();
        int actualRsize = plug.rawDataSize();
        assertEquals(expectedCsize, actualCsize);
        assertEquals(expectedRsize, actualRsize);
    }
}