package ds.prodigy

import android.app.Activity
import android.content.DialogInterface
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem

class BinderDelegate {

    companion object {
        internal val PRESENTER_ID = "KEY_PRESENTER_ID"
    }

    lateinit var presenter: Presenter<IComponent>
    var binding: ViewDataBinding? = null

    private var savedState = false  // for fragment only

    fun onCreate(component: IComponent, savedState: Bundle?) {
        log("${component.javaClass.simpleName} delegated onCreate")

        val id = fetchPresenterId(component, savedState)
        log("presenter id=$id")
        val p = Prodigy.provide(component.javaClass, id) as Presenter<IComponent>

        if (binding == null) {
            val config = Prodigy.getConfig(p.javaClass)
            if (component is Activity)
                binding = DataBindingUtil.setContentView(component, config.layout)
            else if (component is Fragment)
                binding = DataBindingUtil.inflate(LayoutInflater.from(component.activity), config.layout, null, false)
            else
                throw IllegalStateException("This type of BindingAware is not supported")

            val result = binding!!.setVariable(config.bindingVariable, p)
            if (!result)
                throw IllegalStateException("Binding ${config.component.simpleName} to ${config.presenter.simpleName} has been failed")

            p.component = component
            if (p.justCreated) {
                log("${p.javaClass.simpleName} onCreate")
                p.onCreate(savedState)
                p.lifecycleSignal.onNext(LifecycleEvent.CREATE)
            }

            log("${p.javaClass.simpleName} onAttach")
            p.onAttach()
            p.lifecycleSignal.onNext(LifecycleEvent.ATTACH)
            p.justCreated = false
            presenter = p
        }

    }

    @Suppress("SENSELESS_COMPARISON")
    private fun fetchPresenterId(component: IComponent, savedState: Bundle?): Long {
        var id = 0L
        try {
            id = presenter.id
        } catch (e: Exception) {
            id = savedState?.getLong(PRESENTER_ID) ?: when (component) {
                is Activity -> component.intent.getLongExtra(PRESENTER_ID, 0)
                is Fragment -> component.arguments?.getLong(PRESENTER_ID, 0) ?: 0
                else -> 0
            }
        }
        return id
    }

    fun onDestroy(component: IComponent) {
        log("${component.javaClass.simpleName} delegated onDestroy")
        if (presenter.dead) {
            log("already dead")
            return
        }

        if (presenter.isAttached()) {
            log("${presenter.javaClass.simpleName} onDetach")
            presenter.onDetach()
            presenter.lifecycleSignal.onNext(LifecycleEvent.DETACH)
            binding?.unbind()
            binding = null
            presenter.component = null
        }

        val finishing: Boolean
        if (component is Activity) {
            finishing = component.isFinishing
        } else if (component is DialogFragment) {
            finishing = component.isRemoving
        } else if (component is Fragment) {
            log("${component.javaClass.simpleName} isFinishing=${component.activity.isFinishing} isRemoving=${component.isRemoving} stateSaved=$savedState")
            finishing = component.isRemoving || component.activity.isFinishing
        } else {
            throw IllegalStateException("doesnt support yet")
        }

        if (finishing) {
            presenter.fireCallbacks()
            log("${presenter.javaClass.simpleName} onDestroy")
            presenter.onDestroy()
            presenter.lifecycleSignal.onNext(LifecycleEvent.DESTROY)
            Prodigy.remove(presenter)
            presenter.dead = true

        }
    }

    fun onSaveInstanceState(state: Bundle) {
        log("${presenter.javaClass.simpleName} delegated onSaveInstanceState")
        state.putLong(PRESENTER_ID, presenter.id)
        savedState = true
    }

    fun onStart(component: IComponent) {
        log("${component.javaClass.simpleName} delegated onStart")
        presenter.onStart()
    }

    fun onResume(component: IComponent) {
        log("${component.javaClass.simpleName} delegated onResume")
        presenter.onResume()
    }

    fun onPause(component: IComponent) {
        log("${component.javaClass.simpleName} delegated onPause")
        presenter.onPause()
    }

    fun onStop(component: IComponent) {
        log("${component.javaClass.simpleName} delegated onStop")
        presenter.onStop()
    }

    fun onPrepareOptionsMenu(menu: Menu): Boolean {
        log("delegated onPrepareOptionsMenu")
        return presenter.onPrepareOptionsMenu(menu)
    }

    fun onOptionsItemSelected(item: MenuItem): Boolean {
        log("delegated onOptionsItemSelected")
        return presenter.onOptionsItemSelected(item)
    }

    fun finish(component: IComponent) {
        log("${component.javaClass.simpleName} delegated finish")
        when (component) {
            is Activity -> component.finish()
            is DialogFragment -> component.dismiss()
            is Fragment -> component.fragmentManager.popBackStack()
            else -> throw IllegalStateException("finish not implemented properly")
        }
    }

    fun onDialogClick(dialog: DialogInterface, which: Int) {
        log("dialog button clicked")
        presenter.navigator.onDialogButton?.invoke(which)
    }
}