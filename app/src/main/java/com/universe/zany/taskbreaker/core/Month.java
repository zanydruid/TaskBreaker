package com.universe.zany.taskbreaker.core;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Month {

    private int month;
    private List<Day> days;
    private int PRESET;

    public Month(int year, int month) {
        this.month = month % 12;

        // how many days in this month
        int size;
        if (this.month == 0 || this.month == 2 || this.month == 4
                || this.month == 6 || this.month == 7 || this.month == 9 || this.month == 11) {
            size = 31;
        } else if (this.month == 1) {
            if ((year % 4 == 0) && (year % 100 != 0)||(year % 400 == 0)) {
                size = 29;
            } else {
                size = 28;
            }
        } else {
            size = 30;
        }
        this.days = new ArrayList<>();
        // calculate preset
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        PRESET = cal.get(Calendar.DAY_OF_WEEK) - 1;
        for (int i = 0; i < PRESET; i++) {
            Day preDay = new Day(-1);
            this.days.add(preDay);
        }

        int day = 0;
        while (day < size) {
            this.days.add(new Day(day + 1));
            day++;
        }
    }

    /**
     * Fill tasks into correct days in a month
     * @param tasks
     */
    public static Month fillInTasks(List<Task> tasks, int year, int month) {
        Month returnMonth = new Month(year, month);
        for (Task task : tasks) {
            returnMonth.getDay(task.getDay() - 1).addTask(task);
        }
        return returnMonth;
    }

    /**
     * Get number of tasks in a month
     * @return
     */
    public int getTaskNumber() {
        int count = 0;
        for (Day day : days) {
            count += day.getTasks().size();
        }

        return count;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("month " + this.month + " :\n");
        for (Day day : this.days) {
            sb.append("\t" + day.toString() + "\n");
        }
        return sb.toString();
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getSize() {
        return this.days.size();
    }

    public Day getDay(int day) {
        return this.days.get(day + PRESET);
    }

    public List<Day> getALlDays() {
        return this.days;
    }

    public int getPRESET() {
        return PRESET;
    }


}
