package ds.sample.di;

import dagger.Component;
import ds.sample.di.module.ActivityModule;
import ds.sample.di.module.AppModule;

@Component(modules = {AppModule.class})
public interface MainComponent {

    ActivityComponent plus(ActivityModule activityModule);


}
