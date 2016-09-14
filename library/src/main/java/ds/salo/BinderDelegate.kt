package ds.salo

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem

class BinderDelegate {

    lateinit var presenter: Presenter
    var binding: ViewDataBinding? = null

    fun onCreate(ba: BindingAware, savedState: Bundle?) {
        println("delegated onCreate")
        presenter = Salo.provide(ba)

        if (binding == null) {
            val config = Salo.getConfig(ba)
            if (ba is Activity)
                binding = DataBindingUtil.setContentView(ba, config.layout)
            else
                binding = DataBindingUtil.inflate(LayoutInflater.from(ba.getContext()), config.layout, null, false)

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
        println("delegated onDestroy")
        presenter.onDetach()
        presenter.bindingAware = null
        if (ba is Activity) {
            if (ba.isFinishing) {
                Salo.remove(presenter)
                presenter.dead = true
                presenter.onDestroy()
            } else {

            }
        } else {
            throw IllegalStateException("doesnt support yet")
        }

    }

    fun onStart(ba: BindingAware) {
        println("delegated onStart")
        presenter.onStart()
    }

    fun onResume(ba: BindingAware) {
        println("delegated onResume")
        presenter.onResume()
    }

    fun onPause(ba: BindingAware) {
        println("delegated onPause")
        presenter.onPause()
    }

    fun onStop(ba: BindingAware) {
        println("delegated onStop")
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
}