package com.universe.zany.taskbreaker.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.universe.zany.taskbreaker.core.Task;
import com.universe.zany.taskbreaker.data.repository.TaskRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
