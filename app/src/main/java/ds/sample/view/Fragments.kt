package ds.sample.view

import ds.prodigy.BindingFragment
import ds.sample.R

class ListFragment : BindingFragment() {

}

class SimpleFragment : BindingFragment() {
    override fun getTargetLayout() = R.id.root
}