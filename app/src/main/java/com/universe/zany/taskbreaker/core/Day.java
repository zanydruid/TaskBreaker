package com.universe.zany.taskbreaker.core;


import java.util.ArrayList;
import java.util.List;

public class Day {

    public static final int EMPTY = 0;
    public static final int CASUAL = 1;
    public static final int BUSY = 2;
    public static final int SUPER_BUSY = 3;

    private int day;
    private List<Task> tasks;

    public Day(int day, List<Task> tasks) {
        this.day = day;
        this.tasks = tasks;
    }

    public Day(int day){
        this.day = day;
        this.tasks = new ArrayList<>();
    }

    public Task getTask(int position) {
        if (position < 0 || position >= tasks.size()) {
            System.out.println("getTask() at position " + position + " Out of Bound error!!");
            return null;
        }

        return tasks.get(position);
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public int getStatus() {
        if (tasks.size() == 0) {
            return EMPTY;
        } else if (tasks.size() < 5) {
            return CASUAL;
        } else if (tasks.size() < 10) {
            return BUSY;
        } else {
            return SUPER_BUSY;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("day " + this.day + " :\n");
        for (Task task : this.tasks) {
            sb.append("\t" + task.toString() + "\n");
        }
        return sb.toString();
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
