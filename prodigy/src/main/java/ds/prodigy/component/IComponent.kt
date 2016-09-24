package ds.prodigy.component

import android.app.Activity
import android.content.Context
import android.databinding.ViewDataBinding
import ds.prodigy.BinderDelegate

interface IComponent {

    val delegate: BinderDelegate
    val binding: ViewDataBinding

    // helpers
    fun invalidateOptionsMenu()
    fun getContext(): Context
    fun getActivity(): Activity

}