package ds.sample

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import com.facebook.stetho.Stetho
import ds.salo.Salo
import ds.sample.view.*
import ds.sample.viewmodel.*
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

// todo do it with APT
val saloInitializer: Salo.() -> Unit = {
    bind<MainActivity, MainPresenter>(R.layout.activity_main)
    bind<TestActivity, TestPresenter>(R.layout.activity_test)
    bind<TestActivity2, TestPresenter2>(R.layout.activity_test2)
    bind<CallbackActivity, CallbackPresenter>(R.layout.activity_with_callback)
    bind<ViewPagerActivity, ViewPagerPresenter>(R.layout.activity_viewpager)
    bind<CustomComponentActivity, CustomComponentPresenter>(R.layout.activity_test)

    bind<InputDialogFragment, DialogPresenter>(R.layout.dialog_input)
    bind<ListFragment, ListPresenter>(R.layout.fragment_list)
    bind<SimpleFragment, SimpleFragmentPresenter>(R.layout.fragment_simple)
}

