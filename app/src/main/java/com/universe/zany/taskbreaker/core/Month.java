package com.universe.zany.taskbreaker.core;


import java.util.ArrayList;
import java.util.List;

public class Month {

    private int month;
    private List<Day> days;

    public Month(int month, int year) {
        this.month = month;

        int size;
        if (month == 1 || month == 3 || month == 5
                || month == 7 || month == 8 || month == 10 || month == 12) {
            size = 31;
        } else if (month == 2) {
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
