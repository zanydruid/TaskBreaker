package com.universe.zany.taskbreaker.viewmodeltest;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *  sandbox
 */
public class MonthViewModelTest {


    @Test
    public void tests() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.MONTH, 2);
        cal.set(Calendar.DAY_OF_MONTH, 30);
        cal.add(Calendar.MONTH, 1);
        System.out.println(cal.get(Calendar.MONTH) + " " + cal.get(Calendar.DAY_OF_MONTH));

    }
}
