package ds.sample.databinding

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

open class BindingHolder<T : ViewDataBinding>(var binding: T) : RecyclerView.ViewHolder(binding.root)
