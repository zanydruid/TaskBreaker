package com.universe.zany.taskbreaker.repository;

import android.arch.lifecycle.LiveData;

import com.universe.zany.taskbreaker.core.Task;
import com.universe.zany.taskbreaker.dao.TaskDao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TaskRepository {
    private final TaskDao taskDao;

    @Inject
    public TaskRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public LiveData<List<Task>> getTasksNowAndFuture(Date now, int future) {

        if (future > 12) {
            // handle exception
            System.out.println("getTasksNowAndFuture() future: " + future + " is too long!!");
            return null;
        }

        Long start = now.getTime();
        Calendar cal = new GregorianCalendar();
        cal.setTime(now);
        cal.add(Calendar.MONTH, future);
        Long end = cal.getTimeInMillis();

        return this.taskDao.loadTasksInRange(start, end);
    }

    public LiveData<List<Task>> getTasksInADay(Date today) {
        Calendar timeCal = new GregorianCalendar();
        timeCal.setTime(today);
        int year = timeCal.get(Calendar.YEAR);
        int day = timeCal.get(Calendar.DAY_OF_YEAR);
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_YEAR, day);
        Long start = cal.getTimeInMillis();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        Long end = cal.getTimeInMillis();

        return this.taskDao.loadTasksInRange(start, end);
    }

    public LiveData<List<Task>> getTasksInMonth(Date today) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(today);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);

        return this.taskDao.loadTasksInMonth(year, month);
    }

    public LiveData<List<Task>> getTasksByKeyword(String keyword) {
        return this.taskDao.searchTaskByContent("%" + keyword +"%");
    }

    public void createTask(Task task) {
        this.taskDao.insertTask(task);
    }

    public void updateTask(Task task) {
        this.taskDao.updateTasks(task);
    }

    public void deletTask(Task task) {
        this.taskDao.deleteTasks(task);
    }

}
