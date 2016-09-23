package ds.sample

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import com.facebook.stetho.Stetho
import ds.prodigy.Prodigy
import ds.prodigy.gen.GeneratedConfigs
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Prodigy.init(GeneratedConfigs.provide())

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


