package ds.sample.databinding

import android.databinding.ViewDataBinding

class ViewModelHolder<B : ViewDataBinding, VM>(b: B, var viewModel: VM) : BindingHolder<B>(b)
