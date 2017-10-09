package com.universe.zany.taskbreaker;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        int x = 93;
        int y = 73;

        int xStep = 0;
        int yStep = 0;

        while (x > 0) {
            x = x >> 1;
            xStep++;
        }

        while (y > 0) {
            y = y >> 1;
            yStep++;
        }

        System.out.println(Math.abs(xStep - yStep));
    }
}