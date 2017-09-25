package com.universe.zany.taskbreaker.injection;


import com.universe.zany.taskbreaker.view.MonthFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, TaskModule.class})
public interface TaskComponent {

    void inject(MonthFragment fragment);

    final class Initializer {
        private Initializer() {}

        static TaskComponent init(MainApplication app) {
            return DaggerTaskComponent
                    .builder()
                    .appModule(new AppModule(app))
                    .taskModule(new TaskModule(app))
                    .build();
        }
    }

}
