package ds.sample

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import com.facebook.stetho.Stetho
import ds.salo.Salo
import ds.sample.activity.MainActivity
import ds.sample.activity.TestActivity
import ds.sample.activity.TestActivity2
import ds.sample.viewmodel.MainPresenter
import ds.sample.viewmodel.TestPresenter
import ds.sample.viewmodel.TestPresenter2
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Salo.init(saloInitializer)

        initTimber()
        initStetho()

        // enable vector drawables in the resources
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }


    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

}

val saloInitializer: Salo.() -> Unit = {
    bind<MainActivity, MainPresenter>(R.layout.activity_main)
    bind<TestActivity, TestPresenter>(R.layout.activity_test)
    bind<TestActivity2, TestPresenter2>(R.layout.activity_test2)
}

