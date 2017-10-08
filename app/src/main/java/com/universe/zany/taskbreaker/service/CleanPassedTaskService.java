package com.universe.zany.taskbreaker.service;


import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.universe.zany.taskbreaker.core.Task;
import com.universe.zany.taskbreaker.data.repository.TaskRepository;
import com.universe.zany.taskbreaker.injection.MainApplication;

import java.util.List;

import javax.inject.Inject;

public class CleanPassedTaskService extends IntentService{

    TaskRepository repo;

    @Inject
    public CleanPassedTaskService(String name, TaskRepository repository) {
        super(name);
        this.repo = repository;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ((MainApplication)getApplication()).getTaskComponent().inject(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Toast.makeText(getApplicationContext(), "Cleaning passed tasks...", Toast.LENGTH_SHORT).show();
        List<Task> passedTasks = this.repo.getPassedTasks().getValue();
        for (Task task : passedTasks) {
            task.setStatus(Task.Status.FAILED);
        }
        this.repo.updateTasks(passedTasks);
    }
}
