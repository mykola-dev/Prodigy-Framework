package ds.sample.view

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ds.prodigy.component.BindingFragment
import ds.sample.BR
import ds.sample.R
import ds.sample.viewmodel.SimpleFragmentPresenter

class ListFragment : BindingFragment()

class BackStackFragment : BindingFragment() {
    override fun getTargetLayout() = R.id.root
}

class SimpleFragment : BindingFragment() {
    override fun getTargetLayout() = R.id.root
}

class NativeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, R.layout.fragment_simple, null, false)
        val p = SimpleFragmentPresenter()
        binding.setVariable(BR.presenter, p)
        p.text.set("WOOOOOOOW")
        return binding.root
        //return inflater.inflate(R.layout.fragment_simple, container, false)
    }

}