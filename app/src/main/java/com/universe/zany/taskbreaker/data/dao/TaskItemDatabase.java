package com.universe.zany.taskbreaker.data.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.universe.zany.taskbreaker.core.DateConverter;
import com.universe.zany.taskbreaker.core.StatusConverter;
import com.universe.zany.taskbreaker.core.Task;

@Database(entities = {Task.class}, version = 1)
@TypeConverters({DateConverter.class, StatusConverter.class})
public abstract class TaskItemDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();
}
