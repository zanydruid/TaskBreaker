package com.universe.zany.taskbreaker.core;


import java.util.ArrayList;
import java.util.List;

public class Month {

    private int month;
    private List<Day> days;

    public Month(int year, int month) {
        this.month = month % 12;

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
            returnMonth.getDays().get(task.getDay() - 1).addTask(task);
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

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }
}
