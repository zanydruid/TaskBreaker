package com.universe.zany.taskbreaker.core;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Tasks")
public class Task {

    enum Status {
        IN_PROGRESS, COMPLETED, FAILED
    }

    @PrimaryKey(autoGenerate = true)
    public int tId;

    @ColumnInfo(name = "created")
    public Date created;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "deadline")
    public Date deadline;

    @ColumnInfo(name = "status")
    public Status status;

    public Task(Date created, String content, Date deadline) {
        this.created = created;
        this.content = content;
        this.deadline = deadline;
        this.status = Status.IN_PROGRESS;
    }
}
