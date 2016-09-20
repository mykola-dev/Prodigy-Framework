package ds.salo

import android.content.Context
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*

abstract class BindingFragment : Fragment(), IComponent {

    override val delegate: BinderDelegate = BinderDelegate()
    override val binding: ViewDataBinding by lazy { delegate.binding!! }

    //var savedState = false

    override fun invalidateOptionsMenu() {
        activity.invalidateOptionsMenu()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        println("fragment ${javaClass.simpleName} onCreate")
        //delegate.onCreate(this, savedInstanceState)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        println("fragment ${javaClass.simpleName} onCreateView")
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
        println("fragment ${javaClass.simpleName} onDestroyView")
        delegate.onDestroy(this)
        super.onDestroyView()
    }

    override fun onDestroy() {
        println("fragment ${javaClass.simpleName} onDestroy")
        delegate.onDestroy(this)
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        println("fragment ${javaClass.simpleName} onSaveInstanceState")
        delegate.onSaveInstanceState(outState)
        //savedState = true
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