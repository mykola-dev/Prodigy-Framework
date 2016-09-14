package ds.salo

import android.app.Activity
import android.content.Context
import android.databinding.ViewDataBinding

interface BindingAware {
    val delegate: BinderDelegate
    val binding: ViewDataBinding

    // helpers
    val activity: Activity

    fun invalidateOptionsMenu()

    fun getContext(): Context

}