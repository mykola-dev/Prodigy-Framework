package ds.sample.di.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import ds.sample.di.scope.ActivityScope;

@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) { this.activity = activity; }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return activity;
    }

}