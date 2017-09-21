package com.universe.zany.taskbreaker.coretest;


import com.universe.zany.taskbreaker.core.Task;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import static org.junit.Assert.*;

public class  TaskTest {

    @Test
    public void taskTest() {
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Task task = new Task(now, "test task", cal.getTime());

        Calendar nowCal = new GregorianCalendar();
        nowCal.setTime(now);

        int month = nowCal.get(Calendar.MONTH);
        int day = nowCal.get(Calendar.DAY_OF_MONTH);

        assertEquals(month + 1, task.getMonth());
        assertEquals(day + 1, task.getDay());

        System.out.println("task id: " + task.getId() + " created at: " + now.toString());
        System.out.println("deadline at month: " + task.getMonth() + " day: " + task.getDay());
    }
}
