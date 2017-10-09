package com.universe.zany.taskbreaker.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.universe.zany.taskbreaker.data.repository.TaskRepository;

import javax.inject.Inject;

public class HomeViewModel extends ViewModel {

    TaskRepository repo;

    @Inject
    public HomeViewModel(TaskRepository repository) {
        this.repo = repository;
    }

    public LiveData<Integer> getNumOfFailedTask() {
        return this.repo.getNumOfFailedTasks();
    }

    public LiveData<Integer> getNumOfCreatedTask() {
        return this.repo.getNumOfCreatedTasks();
    }
}
