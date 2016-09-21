package ds.sample

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import com.facebook.stetho.Stetho
import ds.prodigy.Prodigy
import ds.sample.view.*
import ds.sample.viewmodel.*
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Prodigy.init(prodigyInitializer)

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
val prodigyInitializer: Prodigy.() -> Unit = {
    // activities
    bind<MainPresenter, MainActivity>(R.layout.activity_main)
    bind<TestPresenter, TestActivity>(R.layout.activity_test)
    bind<TestPresenter2, EmptyActivity>(R.layout.activity_test2)
    bind<CallbackPresenter, EmptyActivity>(R.layout.activity_with_callback)
    bind<ViewPagerPresenter, ViewPagerActivity>(R.layout.activity_viewpager)
    bind<CustomComponentPresenter, CustomComponentActivity>(R.layout.activity_custom)
    bind<ToolBarPresenter, ToolBarActivity>(R.layout.activity_toolbar)
    // fragments
    bind<DialogPresenter, InputDialogFragment>(R.layout.dialog_input)
    bind<ListPresenter, ListFragment>(R.layout.fragment_list)
    bind<SimpleFragmentPresenter, SimpleFragment>(R.layout.fragment_simple)
    bind<ButtonFragmentPresenter, BackStackFragment>(R.layout.fragment_button)
}

