package com.universe.zany.taskbreaker.core;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Items", foreignKeys = @ForeignKey(entity = Task.class,
                                                        parentColumns = "tId",
                                                        childColumns = "task_id"))
public class Item {

    enum Status{
        IN_PROGRESS, COMPLETED
    }

    @PrimaryKey(autoGenerate = true)
    public int iId;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "status")
    public Status status;

    @ColumnInfo(name = "task_id")
    public int tId;

    public Item(String content, int tId){
        this.content = content;
        this.status = Status.IN_PROGRESS;
        this.tId = tId;
    }
}
