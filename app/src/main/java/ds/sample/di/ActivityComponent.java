package ds.sample.di;


import dagger.Subcomponent;
import ds.sample.activity.MainActivity;
import ds.sample.di.module.ActivityModule;
import ds.sample.di.scope.ActivityScope;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity activity);

}
