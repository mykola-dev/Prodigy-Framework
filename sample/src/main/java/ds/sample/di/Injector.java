package ds.sample.di;

import android.app.Activity;

import ds.sample.App;
import ds.sample.di.module.ActivityModule;
import ds.sample.di.module.AppModule;

// just a convenient helper
public class Injector {

    private static MainComponent mainComponent;

    public static MainComponent init(App app) {
        mainComponent = DaggerMainComponent.builder()
                                           .appModule(new AppModule(app))
                                           .build();
        return mainComponent;
    }

    public static MainComponent getMainComponent() {
        return mainComponent;
    }

    public static ActivityComponent getActivityComponent(Activity a) {
        return getMainComponent().plus(new ActivityModule(a));
    }

}
