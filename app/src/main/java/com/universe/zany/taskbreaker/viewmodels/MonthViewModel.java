package com.universe.zany.taskbreaker.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.universe.zany.taskbreaker.core.Task;
import com.universe.zany.taskbreaker.data.repository.TaskRepository;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;


public class MonthViewModel extends ViewModel {
    private int month;
    private LiveData<List<Task>> tasks;
    private TaskRepository repo;

    @Inject
    public MonthViewModel(TaskRepository repo) {
        this.repo = repo;
    }

    public MonthViewModel(){}

    public void init(int year, int month) {

        if (this.tasks != null) {

            return;
        }
        this.month = month;
        this.tasks = repo.getTasksInMonth(year, month);
    }

    public LiveData<List<Task>> getTasks() {
        return this.tasks;
    }

    public String toString() {
        Locale locale = Locale.getDefault();
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.MONTH, this.month);
        return cal.getDisplayName(Calendar.MONTH, Calendar.LONG, locale);
    }

    public int getMonth() {
        return this.month;
    }

}
