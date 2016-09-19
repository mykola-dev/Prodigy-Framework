package ds.salo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

class Navigator(val presenter: Presenter) {

    var onDialogButton: ((Int) -> Unit)? = null

    /**
     * Run Activity
     */
    private fun startActivity(cls: Class<out BindingActivity>, bundle: Bundle? = null) {
        println("startActivity ${cls.name}")
        val a = presenter.getActivity()
        if (a != null) {
            val intent = Intent(a, cls)
            if (bundle != null)
                intent.putExtras(bundle)
            a.startActivity(intent)
        } else
            throw IllegalStateException("activity is null")
    }

    private fun startDialog(cls: Class<out BindingDialogFragment>, bundle: Bundle? = null) {
        println("startDialog ${cls.name}")
        val a = presenter.getActivity()
        if (a != null) {
            val dialogFragment = Fragment.instantiate(a, cls.name, bundle) as DialogFragment
            if (a is FragmentActivity) {
                dialogFragment.show(a.supportFragmentManager, "dialog")
            } else {
                throw IllegalStateException("dialog can start only on FragmentActivity")
            }
        } else
            throw IllegalStateException("activity is null")
    }

    private fun commitFragment(cls: Class<out BindingFragment>, bundle: Bundle?) {
        println("commitFragment ${cls.name}")
        val a = presenter.getActivity()
        if (a != null) {
            val fragment = Fragment.instantiate(a, cls.name, bundle) as BindingFragment
            if (a is FragmentActivity) {
                a.supportFragmentManager
                    .beginTransaction()
                    .replace(fragment.getTargetLayout(), fragment, "fragment")
                    .commitNow()
            } else {
                throw IllegalStateException("fragment can start only on FragmentActivity")
            }
        } else
            throw IllegalStateException("activity is null")
    }

    fun goto(cls: Class<out IComponent>, bundle: Bundle? = null) {
        println("goto ${cls.name} ${if (bundle != null) "with bundle" else ""}")
        if (Activity::class.java.isAssignableFrom(cls)) {
            startActivity(cls as Class<out BindingActivity>, bundle)
        } else if (BindingDialogFragment::class.java.isAssignableFrom(cls)) {
            startDialog(cls as Class<out BindingDialogFragment>, bundle)
        } else if (BindingFragment::class.java.isAssignableFrom(cls)) {
            commitFragment(cls as Class<out BindingFragment>, bundle)
        } else {
            throw IllegalStateException("${cls.name} doesnt support yet")
            // todo fragments/views
        }
    }


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
        goto(config.component, bundle)
        return true
    }


    /**
     * T is callback param type
     */
    /* fun <T : Any> runForResult(p: Presenter, bundle: Bundle? = null, callback: ((T) -> Unit)?): Boolean {
         val config = Salo.getConfig(p.javaClass)

         if (p.isAttached()) {
             println("presenter ${p.javaClass.simpleName} already running. skip any actions")
             return false
         } else if (!Salo.isAwaiting(p)) {
             Salo.putDelayed(config, p)
         }
         *//*if (callback != null)
            p.setCallback(callback)*//*
        goto(config.bindingAware, bundle)
        return true
    }*/

}