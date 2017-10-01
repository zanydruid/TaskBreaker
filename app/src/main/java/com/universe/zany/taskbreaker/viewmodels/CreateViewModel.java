package com.universe.zany.taskbreaker.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.universe.zany.taskbreaker.core.Task;
import com.universe.zany.taskbreaker.data.repository.TaskRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

public class CreateViewModel extends ViewModel{

    private TaskRepository repo;

    @Inject
    public CreateViewModel(TaskRepository repository) {
        this.repo = repository;
    }

    /**
     * Create a single task
     * @param task
     */
    public void createSingleTask(Task task) {
        AsyncCreateSingleTask job = new AsyncCreateSingleTask();
        job.execute(task);
        //this.repo.createTask(task);
    }

    /**
     * Create a daily task for a specific duration
     * @param task
     * @param duration
     */
    public void createDailyTask(Task task, int duration) {
        List<Task> tasks = new ArrayList<>();
        Calendar cal = new GregorianCalendar();
        cal.setTime(task.getDeadline());

        tasks.add(new Task(task));
        for (int i = 0; i < duration; i++) {
            cal.add(Calendar.DAY_OF_YEAR, 1);
            task.setDeadline(cal.getTime());
            tasks.add(new Task(task));
        }

        this.repo.createTasks(tasks);
    }

    /**
     * Create a weekly task in specific duration
     * @param task
     * @param duration
     */
    public void createWeeklyTask(Task task, int duration) {
        List<Task> tasks = new ArrayList<>();
        Calendar cal = new GregorianCalendar();
        cal.setTime(task.getDeadline());

        tasks.add(new Task(task));
        for (int i = 0; i < duration; i = i + 7) {
            cal.add(Calendar.DAY_OF_YEAR, 7);
            task.setDeadline(cal.getTime());
            tasks.add(new Task(task));
        }

        this.repo.createTasks(tasks);
    }

    /**
     * Create a monthly task in specific duration
     * @param task
     * @param duration
     */
    public void createMonthlyTask(Task task, int duration) {
        List<Task> tasks = new ArrayList<>();
        Calendar cal = new GregorianCalendar();
        cal.setTime(task.getDeadline());

        tasks.add(new Task(task));
        int month = duration / 30;
        for (int i = 0;  i < month; i++) {
            cal.add(Calendar.MONTH, 1);
            task.setDeadline(cal.getTime());
            tasks.add(new Task(task));
        }

        this.repo.createTasks(tasks);
    }

    private class AsyncCreateSingleTask extends AsyncTask<Task, Void, Void> {

        @Override
        protected Void doInBackground(Task... tasks) {
            repo.createTask(tasks[0]);
            return null;
        }
    }
}
