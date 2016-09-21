package ds.prodigy

import android.app.Activity
import android.content.Context
import android.databinding.ViewDataBinding

interface IComponent {
    val delegate: BinderDelegate
    val binding: ViewDataBinding

    // helpers
    fun invalidateOptionsMenu()

    fun getContext(): Context
    fun getActivity(): Activity

}