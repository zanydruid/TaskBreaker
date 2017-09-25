package injection;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class AppModule {

    private Application application;

    AppModule(Application app) {
        this.application = app;
    }

    @Singleton
    @Provides
    Application provideApplication() {
        return this.application;
    }
}
