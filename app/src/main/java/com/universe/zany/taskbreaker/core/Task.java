package com.universe.zany.taskbreaker.core;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

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

    public Task(Date created, String content, Date deadline) {
        this.created = created;
        this.content = content;
        this.deadline = deadline;
        this.status = Status.IN_PROGRESS;
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
}
