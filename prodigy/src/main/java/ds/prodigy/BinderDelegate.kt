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
import ds.prodigy.component.IComponent
import ds.prodigy.tools.L
import ds.prodigy.tools.LifecycleEvent

class BinderDelegate {

    val TAG = "delegate"

    companion object {
        internal val PRESENTER_ID = "KEY_PRESENTER_ID"
    }

    lateinit var presenter: Presenter<IComponent>
    internal var binding: ViewDataBinding? = null

    internal fun onCreate(component: IComponent, savedState: Bundle?) {
        L.d(TAG, "onCreate ${component.javaClass.simpleName}")

        if (binding != null) {
            L.w(TAG, "already binded")
            return
        }

        val id = fetchPresenterId(component, savedState)
        L.d(TAG, "presenter id=$id")
        val p = Prodigy.provide(component.javaClass, id) as Presenter<IComponent>
        val config = Prodigy.getConfig(p.javaClass)
        if (component is Activity)
            binding = DataBindingUtil.setContentView(component, config.layout)
        else
            binding = DataBindingUtil.inflate(LayoutInflater.from(component.getActivity()), config.layout, null, false)

        val result = binding!!.setVariable(config.bindingVariable, p)
        if (!result)
            throw IllegalStateException("Binding ${config.component.simpleName} to ${config.presenter.simpleName} has been failed")

        p.component = component
        p.context = component.getContext()

        if (p.justCreated) {
            L.i(TAG, "${p.javaClass.simpleName} onCreate")
            p.onCreate(savedState)
            p.lifecycleSignal.onNext(LifecycleEvent.CREATE)
        }

        L.i(TAG, "${p.javaClass.simpleName} onAttach")
        p.onAttach()
        p.lifecycleSignal.onNext(LifecycleEvent.ATTACH)
        p.justCreated = false
        presenter = p

    }

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

    internal fun onDestroy(c: IComponent) {
        L.d(TAG, "onDestroy ${c.javaClass.simpleName} id=${presenter.id}")
        if (presenter.dead) {
            L.w(TAG, "already dead")
            return
        }

        if (presenter.isAttached()) {
            L.i(TAG, "onDetach ${presenter.javaClass.simpleName}")
            presenter.onDetach()
            presenter.lifecycleSignal.onNext(LifecycleEvent.DETACH)
            binding?.unbind()
            binding = null
            presenter.component = null
        }

        val finishing: Boolean
        if (c is Activity) {
            finishing = c.isFinishing
        } else if (c is DialogFragment) {
            finishing = c.isRemoving
        } else if (c is Fragment) {
            L.d(TAG, "${c.javaClass.simpleName} isFinishing=${c.activity.isFinishing} isRemoving=${c.isRemoving} isDetached=${c.isDetached}")
            finishing = c.isRemoving || c.activity.isFinishing /*|| c.isDetached*/
        } else {
            throw IllegalStateException("doesnt support yet")
        }
        if (finishing) {
            presenter.fireCallbacks()
            L.i(TAG, "${presenter.javaClass.simpleName} onDestroy")
            presenter.onDestroy()
            presenter.lifecycleSignal.onNext(LifecycleEvent.DESTROY)
            Prodigy.remove(presenter)
            presenter.dead = true

        }
    }

    internal fun onSaveInstanceState(state: Bundle) {
        L.d(TAG, "onSaveInstanceState ${presenter.javaClass.simpleName} id=${presenter.id}")
        state.putLong(PRESENTER_ID, presenter.id)
    }

    internal fun onStart(component: IComponent) {
        presenter.onStart()
    }

    internal fun onResume(component: IComponent) {
        presenter.onResume()
    }

    internal fun onPause(component: IComponent) {
        presenter.onPause()
    }

    internal fun onStop(component: IComponent) {
        presenter.onStop()
    }

    internal fun onPrepareOptionsMenu(menu: Menu): Boolean {
        return presenter.onPrepareOptionsMenu(menu)
    }

    internal fun onOptionsItemSelected(item: MenuItem): Boolean {
        return presenter.onOptionsItemSelected(item)
    }

    internal fun onDialogClick(dialog: DialogInterface, which: Int) {
        presenter.navigator.onDialogButton?.invoke(which)
    }
}