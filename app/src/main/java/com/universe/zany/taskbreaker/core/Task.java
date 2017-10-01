package com.universe.zany.taskbreaker.core;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity(tableName = "Tasks")
public class Task {

    public enum Status {
        IN_PROGRESS, COMPLETED, FAILED
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "created")
    private Date created;

    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "deadline")
    private Date deadline;

    @ColumnInfo(name = "status")
    private Status status;

    @ColumnInfo(name = "year")
    private int year;

    @ColumnInfo(name = "month")
    private int month;

    @ColumnInfo(name = "day")
    private int day;

    /**
     * -1 - not specified
     * 0 - Single
     * 1 - Daily
     * 2 - Weekly
     * 3 - Monthly
     */
    @Ignore
    private int type = -1;

    public Task(Date created, String content, Date deadline) {
        this.created = created;
        this.content = content;
        this.deadline = deadline;
        this.status = Status.IN_PROGRESS;
        Calendar cal = new GregorianCalendar();
        cal.setTime(deadline);
        this.year = cal.get(Calendar.YEAR);
        this.month = cal.get(Calendar.MONTH);
        this.day = cal.get(Calendar.DAY_OF_MONTH);
    }

    @Ignore
    public Task() {}

    @Ignore
    public Task(Task task) {
        this(task.getCreated(), task.getContent(), task.getDeadline());
    }

    public String toString() {
        return "task: " + this.content +
                " created at: " + this.created +
                " deadline at: " + this.deadline;
    }

    // getters and setters

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
