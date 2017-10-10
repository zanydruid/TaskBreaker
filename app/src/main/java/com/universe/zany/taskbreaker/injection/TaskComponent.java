package com.universe.zany.taskbreaker.injection;


import com.universe.zany.taskbreaker.service.CleanPassedTaskService;
import com.universe.zany.taskbreaker.service.DailyNotifyService;
import com.universe.zany.taskbreaker.view.CreateFragment;
import com.universe.zany.taskbreaker.view.DayFragment;
import com.universe.zany.taskbreaker.view.HomeFragment;
import com.universe.zany.taskbreaker.view.MonthFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, TaskModule.class})
public interface TaskComponent {

    void inject(MonthFragment fragment);
    void inject(CreateFragment fragment);
    void inject(DayFragment fragment);
    void inject(HomeFragment fragment);
    void inject(CleanPassedTaskService service);
    void inject(DailyNotifyService service);


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
