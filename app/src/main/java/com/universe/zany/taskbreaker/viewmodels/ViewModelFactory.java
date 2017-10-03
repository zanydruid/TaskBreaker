package com.universe.zany.taskbreaker.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.universe.zany.taskbreaker.data.repository.TaskRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final TaskRepository repository;

    @Inject
    public ViewModelFactory(TaskRepository repository) {

        this.repository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MonthViewModel.class)) {
            return (T) new MonthViewModel(repository);
        } else if (modelClass.isAssignableFrom(CreateViewModel.class)) {
            return (T) new CreateViewModel(repository);
        } else if (modelClass.isAssignableFrom(DayViewModel.class)) {
            return (T) new DayViewModel(repository);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
