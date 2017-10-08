package com.universe.zany.taskbreaker.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.universe.zany.taskbreaker.core.Task;

import java.util.List;

import javax.inject.Inject;


@Dao
public interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(Task... tasks);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTasks(List<Task> tasks);

    @Query("SELECT * FROM Tasks")
    LiveData<List<Task>> loadAllTask();

    @Query("SELECT * FROM Tasks WHERE deadline BETWEEN :start AND :end")
    LiveData<List<Task>> loadTasksInRange(Long start, Long end);

    @Query("SELECT * FROM Tasks WHERE content LIKE :keyword")
    LiveData<List<Task>> searchTaskByContent(String keyword);

    @Query("SELECT * FROM Tasks WHERE id = :taskId")
    LiveData<Task> findTaskById(int taskId);

    @Query("SELECT * FROM Tasks WHERE year = :year AND month = :month AND status = 0")
    LiveData<List<Task>> loadTasksInMonth(int year, int month);

    @Query("SELECT * FROM Tasks WHERE year = :year AND month = :month AND day = :day AND status = 0")
    LiveData<List<Task>> loadTasksInDay(int year, int month, int day);

    @Query("SELECT * FROM Tasks WHERE status = 0 AND deadline < :today")
    LiveData<List<Task>> loadAllPassedTasks(Long today);

    @Update
    void updateTask(Task... task);

    @Update
    void updateTasks(List<Task> tasks);

    @Query("SELECT COUNT(*) FROM Tasks")
    LiveData<Integer> loadNumOfCreatedTasks();

    @Query("SELECT COUNT(*) FROM Tasks WHERE status = -1")
    LiveData<Integer> loadNumOfFailedTasks();

    @Delete
    void deleteTasks(Task... tasks);
}

