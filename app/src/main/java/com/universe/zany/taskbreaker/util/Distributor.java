package com.universe.zany.taskbreaker.util;


import com.universe.zany.taskbreaker.core.Day;
import com.universe.zany.taskbreaker.core.Month;
import com.universe.zany.taskbreaker.core.Task;
import com.universe.zany.taskbreaker.repository.TaskRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Distributor {
    public static final int FUTURE_LENGTH = 3;

    public static List<Month> fillTasksInMonth(Date today, List<Task> tasks) {
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


}
