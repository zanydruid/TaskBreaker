package com.universe.zany.taskbreaker.util;


import com.universe.zany.taskbreaker.core.Day;
import com.universe.zany.taskbreaker.core.Month;
import com.universe.zany.taskbreaker.core.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Distributor {
    public static final int FUTURE_LENGTH = 6;

    public static List<Month> fillTasksInNowAndFutureMonth(Date today, List<Task> tasks) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(today);
        int startMonth = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        List<Month> resultsMonth = new ArrayList<>();
        // add 6 months to list
        for (int i = startMonth; i < startMonth + Distributor.FUTURE_LENGTH; i++) {
            resultsMonth.add(new Month(i + 1, year));
        }

        for (Task task : tasks) {
            int taskMonth = task.getMonth();
            int taskDay = task.getDay();
            Day assignedDay = resultsMonth.get(taskMonth - startMonth).getDays().get(taskDay - 1);
            assignedDay.addTask(task);
        }

        return resultsMonth;
    }

    public static Month fillTaskInMonth(Date today, List<Task> tasks) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(today);

        Month month = new Month(cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));

        for (Task task : tasks) {
            month.getDays().get(task.getDay()).addTask(task);
        }

        return month;
    }

}
