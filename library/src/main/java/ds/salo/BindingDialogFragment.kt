package ds.salo

import android.app.Activity
import android.databinding.ViewDataBinding
import android.support.v4.app.DialogFragment

abstract class BindingDialogFragment : DialogFragment(), BindingAware {

    override val delegate: BinderDelegate = BinderDelegate()
    override val binding: ViewDataBinding by lazy { delegate.binding!! }
    override val activity: Activity by lazy { getActivity() }

    override fun invalidateOptionsMenu() {
        activity.invalidateOptionsMenu()
    }
}