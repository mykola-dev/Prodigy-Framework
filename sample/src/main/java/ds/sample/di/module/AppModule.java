package ds.sample.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ds.sample.App;

/**
 * Provides {@link App} instance dependency.
 */
@Module
public class AppModule {

    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    App provideApp() {
        return app;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return app.getApplicationContext();
    }
}
