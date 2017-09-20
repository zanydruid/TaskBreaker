package com.universe.zany.taskbreaker.core;

import android.arch.lifecycle.LiveData;

import java.util.List;

public class Day {
    private int day;
    private List<Task> tasks;

    public Day(int day, List<Task> tasks) {
        this.day = day;
        this.tasks = tasks;
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
