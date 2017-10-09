package com.universe.zany.taskbreaker.service;


import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.universe.zany.taskbreaker.core.Task;
import com.universe.zany.taskbreaker.data.repository.TaskRepository;
import com.universe.zany.taskbreaker.injection.MainApplication;

import java.util.List;

import javax.inject.Inject;

public class CleanPassedTaskService extends IntentService{

    private static final String TAG = "CleanPassedTasksService";

    @Inject
    TaskRepository repo;

    public CleanPassedTaskService() {
        super("Clean passed tasks service.");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ((MainApplication)getApplication()).getTaskComponent().inject(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "cleaning passed tasks...");
        //Toast.makeText(getApplicationContext(), "Cleaning passed tasks...", Toast.LENGTH_SHORT).show();
        List<Task> passedTasks = this.repo.getPassedTasks().getValue();
        if (passedTasks == null) {
            return;
        }
        for (Task task : passedTasks) {
            task.setStatus(Task.Status.FAILED);
        }
        this.repo.updateTasks(passedTasks);
    }
}
