package ds.sample.adapter

import android.content.Context
import android.databinding.ViewDataBinding
import ds.sample.R
import ds.sample.data.Name
import ds.sample.databinding.ViewModelAdapter
import ds.sample.viewmodel.NameViewModel

class NamesAdapter(ctx: Context, data: List<Name>) : ViewModelAdapter<NameViewModel, Name>(ctx, data) {

    override fun getLayoutId(): Int = R.layout.item_name
    override fun instantiateViewModel(): NameViewModel = NameViewModel()

    override fun onFillViewModel(binding: ViewDataBinding, viewModel: NameViewModel, item: Name, position: Int) {
        viewModel.first = item.first
        viewModel.last = item.last
    }

}