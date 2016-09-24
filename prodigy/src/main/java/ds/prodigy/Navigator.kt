package ds.prodigy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import ds.prodigy.component.BindingActivity
import ds.prodigy.component.BindingDialogFragment
import ds.prodigy.component.BindingFragment
import ds.prodigy.component.IComponent
import ds.prodigy.tools.L

open class Navigator(val presenter: Presenter<*>) {
    val TAG = "navigator"

    var onDialogButton: ((Int) -> Unit)? = null

    /**
     * Run Activity
     */
    open protected fun startActivity(cls: Class<out BindingActivity>, bundle: Bundle? = null, flags: Int = 0) {
        L.v(TAG, "startActivity ${cls.name}")
        val a = presenter.getActivity()
        if (a != null) {
            val intent = Intent(a, cls).addFlags(flags)
            if (bundle != null)
                intent.putExtras(bundle)
            a.startActivity(intent)
        } else
            throw IllegalStateException("activity is null")
    }

    open protected fun startDialog(cls: Class<out BindingDialogFragment>, bundle: Bundle? = null) {
        L.v(TAG, "startDialog ${cls.name}")
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

    open protected fun commitFragment(cls: Class<out BindingFragment>, bundle: Bundle?, addToBackstack: Boolean = false) {
        L.v(TAG, "commitFragment ${cls.name}")
        val a = presenter.getActivity()
        if (a != null) {
            val fragment = Fragment.instantiate(a, cls.name, bundle) as BindingFragment
            if (a is FragmentActivity) {
                val transaction = a
                    .supportFragmentManager
                    .beginTransaction()
                if (addToBackstack)
                    transaction.addToBackStack(null).add(fragment.getTargetLayout(), fragment, "fragment")
                else
                    transaction.replace(fragment.getTargetLayout(), fragment, "fragment")

                transaction.commit()    //.commitNow() todo migrate to v24
            } else {
                throw IllegalStateException("fragment can start only on FragmentActivity")
            }
        } else
            throw IllegalStateException("activity is null")
    }

    open fun runComponent(cls: Class<out IComponent>, bundle: Bundle? = null) {
        L.v(TAG, "goto ${cls.name} ${if (bundle != null) "with bundle" else ""}")
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
    open fun run(p: Presenter<*>, bundle: Bundle = Bundle()): Boolean {
        val config = Prodigy.getConfig(p.javaClass)

        if (p.isAttached()) {
            L.e(TAG, "presenter ${p.javaClass.simpleName} already running. skip any actions")
            return false
        } else if (!Prodigy.isAwaiting(p)) {
            Prodigy.putDelayed(p)
        }
        bundle.putLong(BinderDelegate.PRESENTER_ID, p.id)
        runComponent(config.component, bundle)
        return true
    }

    // fragments version
    fun run(p: Presenter<*>, bundle: Bundle = Bundle(), addToBackstack: Boolean = false): Boolean {
        val config = Prodigy.getConfig(p.javaClass)

        if (p.isAttached()) {
            L.e(TAG, "presenter ${p.javaClass.simpleName} already running. skip any actions")
            return false
        } else if (!Prodigy.isAwaiting(p)) {
            Prodigy.putDelayed(p)
        }
        bundle.putLong(BinderDelegate.PRESENTER_ID, p.id)
        if (BindingFragment::class.java.isAssignableFrom(config.component))
            commitFragment(config.component as Class<out BindingFragment>, bundle, addToBackstack)
        else
            throw IllegalStateException("Not a Fragment")

        return true
    }

    // finish activity, back to fragment stack, close dialog etc.
    fun finish() {
        with(presenter.component) {
            when (this) {
                is Activity -> finish()
                is DialogFragment -> dismiss()
                is Fragment -> {
                    if (fragmentManager.backStackEntryCount > 0)
                        fragmentManager.popBackStackImmediate()
                    else
                        activity.finish()
                }
                else -> throw IllegalStateException("finish not implemented properly")
            }
        }
    }

}