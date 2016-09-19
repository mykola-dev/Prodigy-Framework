package ds.salo

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
        private val ID = "KEY_ID"
    }

    lateinit var presenter: Presenter
    var binding: ViewDataBinding? = null
    var presenterId = 0L    // used with fragments

    fun onCreate(component: IComponent, savedState: Bundle?) {
        println("${component.javaClass.simpleName} delegated onCreate")

        val id = savedState?.getLong(ID) ?: presenterId
        println("presenter id=$id")
        presenter = Salo.provide(component, id)

        if (binding == null) {
            val config = Salo.getConfig(component)
            if (component is Activity)
                binding = DataBindingUtil.setContentView(component, config.layout)
            else if (component is Fragment)
                binding = DataBindingUtil.inflate(LayoutInflater.from(component.activity), config.layout, null, false)
            else
                throw IllegalStateException("This type of BindingAware is not supported")

            val result = binding!!.setVariable(config.bindingVariable, presenter)
            if (!result)
                throw IllegalStateException("Binding ${config.component.simpleName} to ${config.presenter.simpleName} has been failed")

            presenter.component = component
            if (presenter.justCreated) {
                presenter.onCreate()
            }

            presenter.onAttach()
            presenter.justCreated = false
        }

    }

    fun onDestroy(component: IComponent) {
        println("${component.javaClass.simpleName} delegated onDestroy")
        if (presenter.dead) {
            println("already dead")
            return
        }

        if (presenter.isAttached()) {
            presenter.onDetach()
            binding?.unbind()
            binding = null
            presenter.component = null
            presenterId=presenter.id
        }

        val finishing: Boolean
        if (component is Activity) {
            finishing = component.isFinishing
        } else if (component is DialogFragment) {
            finishing = component.isRemoving
        } else if (component is Fragment) {
            println("${component.javaClass.simpleName} isFinishing=${component.activity.isFinishing} isRemoving=${component.isRemoving}}")
            finishing = component.isRemoving || component.activity.isFinishing
        } else {
            throw IllegalStateException("doesnt support yet")
        }

        if (finishing) {
            presenter.fireCallbacks()
            Salo.remove(presenter)
            presenter.onDestroy()
            presenter.dead = true

        }
    }

    fun onSaveInstanceState(state: Bundle) {
        println("delegated onSaveInstanceState")
        state.putLong(ID, presenter.id)
    }

    fun onStart(component: IComponent) {
        println("${component.javaClass.simpleName} delegated onStart")
        presenter.onStart()
    }

    fun onResume(component: IComponent) {
        println("${component.javaClass.simpleName} delegated onResume")
        presenter.onResume()
    }

    fun onPause(component: IComponent) {
        println("${component.javaClass.simpleName} delegated onPause")
        presenter.onPause()
    }

    fun onStop(component: IComponent) {
        println("${component.javaClass.simpleName} delegated onStop")
        presenter.onStop()
    }

    fun onPrepareOptionsMenu(menu: Menu): Boolean {
        println("delegated onPrepareOptionsMenu")
        return presenter.onPrepareOptionsMenu(menu)
    }

    fun onOptionsItemSelected(item: MenuItem): Boolean {
        println("delegated onOptionsItemSelected")
        return presenter.onOptionsItemSelected(item)
    }

    fun finish(component: IComponent) {
        println("${component.javaClass.simpleName} delegated finish")
        when (component) {
            is Activity -> component.finish()
            is DialogFragment -> component.dismiss()
            is Fragment -> component.fragmentManager.popBackStack()
            else -> throw IllegalStateException("finish not implemented properly")
        }
    }

    fun onDialogClick(dialog: DialogInterface, which: Int) {
        println("dialog button clicked")
        presenter.navigator.onDialogButton?.invoke(which)
    }
}