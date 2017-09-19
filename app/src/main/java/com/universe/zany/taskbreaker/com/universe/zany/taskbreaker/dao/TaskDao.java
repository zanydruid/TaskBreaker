package com.universe.zany.taskbreaker.com.universe.zany.taskbreaker.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.universe.zany.taskbreaker.core.Task;

import java.util.List;


@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(Task... tasks);

    @Query("SELECT * FROM Tasks")
    LiveData<List<Task>> loadAllTask();

    @Query("SELECT * FROM Tasks WHERE deadline > :now")
    LiveData<List<Task>> loadTaskFromDeadline(Long now);

    @Query("SELECT * FROM Tasks WHERE content LIKE :keyword")
    LiveData<List<Task>> searchTaskByContent(String keyword);

    @Query("SELECT * FROM Tasks WHERE id = :taskId")
    LiveData<Task> findTaskById(int taskId);

    @Update
    void updateTasks(Task... task);

    @Delete
    void deleteTasks(Task... tasks);
}

