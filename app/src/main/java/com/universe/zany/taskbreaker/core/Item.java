package com.universe.zany.taskbreaker.core;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Items", foreignKeys = @ForeignKey(entity = Task.class,
                                                        parentColumns = "id",
                                                        childColumns = "task_id"))
public class Item {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "task_id")
    private int taskId;

    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "status")
    private Task.Status status;

    public Item(int taskId, String content) {
        this.taskId = taskId;
        this.content = content;
        this.status = Task.Status.IN_PROGRESS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Task.Status getStatus() {
        return status;
    }

    public void setStatus(Task.Status status) {
        this.status = status;
    }
}
