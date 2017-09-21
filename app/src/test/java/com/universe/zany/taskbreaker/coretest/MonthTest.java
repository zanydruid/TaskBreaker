package com.universe.zany.taskbreaker.coretest;

import com.universe.zany.taskbreaker.core.Month;

import org.junit.Test;
import static org.junit.Assert.*;

public class MonthTest {

    @Test
    public void tests() {
        // Jan 2017
        Month month1 = new Month(1, 2017);
        assertEquals(31, month1.getDays().size());

        // Feb 2017
        Month month2 = new Month(2, 2017);
        assertEquals(28, month2.getDays().size());

        // Apr 2015
        Month month3 = new Month(4, 2015);
        assertEquals(30, month3.getDays().size());

        // Feb 2004
        Month month4 = new Month(2, 2004);
        assertEquals(29, month4.getDays().size());

        // Feb 1900
        Month month5 = new Month(2, 1900);
        assertEquals(28, month5.getDays().size());
    }
}
