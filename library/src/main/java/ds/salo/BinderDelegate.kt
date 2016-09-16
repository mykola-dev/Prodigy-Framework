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

    lateinit var presenter: Presenter
    var binding: ViewDataBinding? = null

    fun onCreate(ba: BindingAware, savedState: Bundle?) {
        println("${ba.javaClass.simpleName} delegated onCreate")

        val id = savedState?.getLong(ID) ?: 0
        presenter = Salo.provide(ba, id)

        if (binding == null) {
            val config = Salo.getConfig(ba)
            if (ba is Activity)
                binding = DataBindingUtil.setContentView(ba, config.layout)
            else if (ba is Fragment)
                binding = DataBindingUtil.inflate(LayoutInflater.from(ba.getContext()), config.layout, null, false)
            else
                throw IllegalStateException("This type of BindingAware is not supported")

            val result = binding!!.setVariable(config.bindingVariable, presenter)
            if (!result)
                throw IllegalStateException("Binding ${config.bindingAware.simpleName} to ${config.presenter.simpleName} has been failed")

            presenter.bindingAware = ba
            if (presenter.justCreated) {
                presenter.onCreate()
            }

            presenter.onAttach()
            presenter.justCreated = false
        }

    }

    fun onDestroy(ba: BindingAware) {
        println("${ba.javaClass.simpleName} delegated onDestroy")
        if (presenter.dead)
            return

        presenter.onDetach()
        presenter.bindingAware = null
        val finishing: Boolean
        if (ba is Activity) {
            finishing = ba.isFinishing
        } else if (ba is DialogFragment) {
            finishing = ba.isRemoving
            /* } else if (ba is Fragment){
                 // todo*/
        } else {
            throw IllegalStateException("doesnt support yet")
        }

        if (finishing) {
            presenter.fireCallbacks()
            Salo.remove(presenter)
            presenter.dead = true
            presenter.onDestroy()
            binding?.unbind()
            binding = null
        }
    }


    fun onSaveInstanceState(state: Bundle) {
        println("delegated onSaveInstanceState")
        state.putLong(ID, presenter.id)
    }

    fun onStart(ba: BindingAware) {
        println("${ba.javaClass.simpleName} delegated onStart")
        presenter.onStart()
    }

    fun onResume(ba: BindingAware) {
        println("${ba.javaClass.simpleName} delegated onResume")
        presenter.onResume()
    }

    fun onPause(ba: BindingAware) {
        println("${ba.javaClass.simpleName} delegated onPause")
        presenter.onPause()
    }

    fun onStop(ba: BindingAware) {
        println("${ba.javaClass.simpleName} delegated onStop")
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

    companion object {
        private val ID = "KEY_ID"
    }

    fun finish(ba: BindingAware) {
        println("${ba.javaClass.simpleName} delegated finish")
        when (ba) {
            is Activity -> ba.finish()
            is DialogFragment -> ba.dismiss()
            is Fragment -> ba.fragmentManager.popBackStack()
            else -> throw IllegalStateException("finish not implemented properly")
        }
    }

    fun onDialogClick(dialog: DialogInterface, which: Int) {
        println("dialog button clicked")
        presenter.navigator.onDialogButton?.invoke(which)
    }
}