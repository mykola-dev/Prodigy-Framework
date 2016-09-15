package ds.salo

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

abstract class Presenter() {
    companion object {
        var idGenerator: Long = 1
            get() = field++
    }

    var id = idGenerator
    val navigator by lazy { Navigator(this) }
    var bindingAware: BindingAware? = null
    var dead = false
    var justCreated = true

    //val lifeCycleSignal: BehaviorSubject

    /** Convenient helper to navigate between presenters
     * @return true if success
     */
    fun run(p: Presenter, bundle: Bundle? = null): Boolean {
        val config = Salo.getConfig(p.javaClass)

        if (p.isAttached()) {
            println("presenter ${p.javaClass.simpleName} already running. skip any actions")
            return false
        } else if (!Salo.isAwaiting(p)) {
            Salo.putDelayed(config, p)
        }
        navigator.goto(config.bindingAware, bundle)
        return true
    }

    fun isAttached() = bindingAware != null

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// lifecycle and callbacks

    open fun onCreate() = Unit

    /** BindingAware has been attached to presenter */
    open fun onAttach() = Unit

    /** BindingAware has been detached from presenter */
    open fun onDetach() = Unit

    /** Presenter has been destroyed and shouldn't be used. dead flag is true from now */
    open fun onDestroy() = Unit

    open fun onStart() = Unit
    open fun onStop() = Unit
    open fun onResume() = Unit
    open fun onPause() = Unit

    // menu stuff
    open fun onOptionsItemSelected(item: MenuItem): Boolean = false

    open fun onPrepareOptionsMenu(menu: Menu): Boolean = true

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    internal fun getActivity(): Activity? {
        return bindingAware?.activity
    }


}