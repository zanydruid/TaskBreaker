package com.universe.zany.taskbreaker.coretest;

import com.universe.zany.taskbreaker.core.Month;

import org.junit.Test;
import static org.junit.Assert.*;

public class MonthTest {

    @Test
    public void tests() {
        // Jan 2017
        Month month1 = new Month(2017, 0);
        assertEquals(31, month1.getDays().size());

        // Feb 2017
        Month month2 = new Month(2017, 13);
        assertEquals(28, month2.getDays().size());

        // Apr 2015
        Month month3 = new Month(2015, 3);
        assertEquals(30, month3.getDays().size());

        // Feb 2004
        Month month4 = new Month(2004, 1);
        assertEquals(29, month4.getDays().size());

        // Feb 1900
        Month month5 = new Month(1900, 1);
        assertEquals(28, month5.getDays().size());
    }
}
