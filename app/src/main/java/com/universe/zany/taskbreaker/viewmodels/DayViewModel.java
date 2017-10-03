package com.universe.zany.taskbreaker.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.universe.zany.taskbreaker.core.Task;
import com.universe.zany.taskbreaker.data.repository.TaskRepository;

import java.util.List;

import javax.inject.Inject;

public class DayViewModel extends ViewModel {

    private TaskRepository repo;

    @Inject
    public DayViewModel(TaskRepository repository) {
        this.repo = repository;
    }

    public LiveData<List<Task>> getTaskInDay(int year, int month, int day) {
        return this.repo.getTasksInDay(year, month, day);
    }

    public void completeTask(Task task) {
        task.setStatus(Task.Status.COMPLETED);
        new AsyncCompleteTask().execute(task);
    }

    private class AsyncCompleteTask extends AsyncTask<Task, Void, Void> {

        @Override
        protected Void doInBackground(Task... tasks) {
            repo.updateTask(tasks[0]);
            return null;
        }
    }
}
