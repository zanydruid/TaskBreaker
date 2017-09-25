package injection;


import android.app.Application;

import com.universe.zany.taskbreaker.core.Task;

public class MainApplication extends Application {

    private TaskComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = TaskComponent.Initializer.init(this);
    }

    public TaskComponent getTaskComponent() {
        return this.component;
    }
}
