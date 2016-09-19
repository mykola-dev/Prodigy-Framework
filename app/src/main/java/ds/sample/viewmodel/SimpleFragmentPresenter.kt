package ds.sample.viewmodel

import android.databinding.ObservableField
import ds.salo.Presenter
import ds.sample.util.L

class SimpleFragmentPresenter(text: String? = null) : Presenter() {
    val text = ObservableField<String>()

    init {
        this.text.set(text ?: "id=$id")
    }

    override fun onCreate() {
        L.v("Simple created")
    }

    override fun onAttach() {
        L.v("Simple attached")
    }

    override fun onDetach() {
        L.v("Simple detached")
    }

    override fun onDestroy() {
        L.v("Simple destroyed")
    }


}