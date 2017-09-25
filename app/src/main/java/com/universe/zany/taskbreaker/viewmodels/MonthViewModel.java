package com.universe.zany.taskbreaker.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.universe.zany.taskbreaker.core.Task;
import com.universe.zany.taskbreaker.data.repository.TaskRepository;

import java.util.List;

import javax.inject.Inject;


public class MonthViewModel extends ViewModel {

    private TaskRepository repo;

    @Inject
    public MonthViewModel(TaskRepository repo) {
        this.repo = repo;
    }

    public LiveData<List<Task>> getTasksByMonth(int year, int month) {
        return this.repo.getTasksInMonth(year, month);
    }
}
