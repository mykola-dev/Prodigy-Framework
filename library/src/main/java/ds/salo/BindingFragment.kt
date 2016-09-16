package ds.salo

import android.app.Activity
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BindingFragment : Fragment(), BindingAware {

    override val delegate: BinderDelegate = BinderDelegate()
    override val binding: ViewDataBinding by lazy { delegate.binding!! }
    override val activity: Activity by lazy { getActivity() }

    override fun invalidateOptionsMenu() {
        activity.invalidateOptionsMenu()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        delegate.onCreate(this, savedInstanceState)
        return binding.root
    }
}