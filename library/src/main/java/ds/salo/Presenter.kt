package ds.salo

import android.app.Activity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

abstract class Presenter() {

    var id = idGenerator
    val navigator by lazy { Navigator(this) }
    var bindingAware: BindingAware? = null
    var dead = false
    var justCreated = true

    val results = mutableListOf<Result<Any>>()

    inline fun <reified T : Any> Presenter.setCallback(noinline cb: (T?) -> Unit) {
        val cls: Class<T> = T::class.java
        val result: Result<T> = Result(cls, cb)
        result.owner = this
        results.add(result as Result<Any>)
    }

    protected inline fun <reified T : Any> setResult(result: T?) {
        val r: Result<in T> = results.first { T::class.java == it.cls }
        r.result = result
    }

    //val lifeCycleSignal: BehaviorSubject

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

    // finish activity, back to fragment stack, close dialog etc.
    fun finish() {
        bindingAware?.delegate?.finish(bindingAware!!)
    }

    fun fireCallbacks() {
        results.forEach {
            //if (it.owner?.isAttached() ?: false) {
                it.callback.invoke(it.result)
                it.owner = null
            //}
        }
        results.clear()
    }

    internal fun getActivity(): Activity? {
        return bindingAware?.activity
    }

    fun toast(text: String?) {
        Toast.makeText(bindingAware?.getContext(), text, Toast.LENGTH_SHORT).show()
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    companion object {
        var idGenerator: Long = 1
            get() = field++
    }
}