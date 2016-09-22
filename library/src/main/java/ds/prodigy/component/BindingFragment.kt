package ds.prodigy.component

import android.content.Context
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import ds.prodigy.BinderDelegate
import ds.prodigy.L

abstract class BindingFragment : Fragment(), IComponent {
    val TAG="F"

    override val delegate: BinderDelegate = BinderDelegate()
    override val binding: ViewDataBinding by lazy { delegate.binding!! }

    override fun invalidateOptionsMenu() {
        activity.invalidateOptionsMenu()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        L.v(TAG, "onCreate")
        delegate.onCreate(this, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        L.v(TAG, "onCreateView")
        delegate.onCreate(this, savedInstanceState)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        delegate.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        delegate.onPause(this)
    }

    override fun onDestroyView() {
        L.v(TAG, "onDestroyView")
        delegate.onDestroy(this)
        super.onDestroyView()
    }

    override fun onDestroy() {
        L.v(TAG, "onDestroy")
        delegate.onDestroy(this)
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        delegate.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun getContext(): Context {
        return activity.applicationContext
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        delegate.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return delegate.onOptionsItemSelected(item)
    }

    open fun getTargetLayout() = android.R.id.content


}