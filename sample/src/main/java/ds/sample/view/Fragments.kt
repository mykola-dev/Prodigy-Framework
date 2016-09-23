package ds.sample.view

import ds.prodigy.component.BindingFragment
import ds.sample.R

class ListFragment : BindingFragment()

class BackStackFragment : BindingFragment() {
    override fun getTargetLayout() = R.id.root
}

class SimpleFragment : BindingFragment() {
    override fun getTargetLayout() = R.id.root
}
