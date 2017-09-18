package com.universe.zany.taskbreaker.com.universe.zany.taskbreaker.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.universe.zany.taskbreaker.core.Task;

import java.util.List;


@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(Task... task);

    @Query("SELECT * FROM Tasks")
    List<Task> loadAllTask();

    @Query("SELECT * FROM Tasks WHERE deadline > :now")
    List<Task> loadTaskFromDeadline(Long now);

    @Query("SELECT * FROM Tasks WHERE content LIKE :keyword")
    List<Task> searchTaskByContent(String keyword);

    @Query("SELECT * FROM Tasks WHERE tId = :taskId")
    Task findTaskById(int taskId);
}

