package com.universe.zany.taskbreaker.injection;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;


import com.universe.zany.taskbreaker.data.dao.TaskDao;
import com.universe.zany.taskbreaker.data.dao.TaskDatabase;
import com.universe.zany.taskbreaker.data.repository.TaskRepository;
import com.universe.zany.taskbreaker.viewmodels.ViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class TaskModule {

    private final TaskDatabase database;

    TaskModule(Application app) {
        database = Room.databaseBuilder(
                app,
                TaskDatabase.class,
                "Task.db"
        ).build();
    }

    @Singleton
    @Provides
    TaskRepository provideTaskRepository(TaskDao dao) {
        return new TaskRepository(dao);
    }

    @Singleton
    @Provides
    TaskDao provideTaskDao() {
        return database.taskDao();
    }

    @Singleton
    @Provides
    ViewModelProvider.Factory provideViewModelFactory(TaskRepository repo) {
        return new ViewModelFactory(repo);
    }

}
