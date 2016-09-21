package ds.sample.viewmodel

import android.databinding.ObservableField
import ds.prodigy.IComponent
import ds.prodigy.Presenter

class SimpleFragmentPresenter(text: String? = null) : Presenter<IComponent>() {
    val text = ObservableField<String>()

    init {
        this.text.set(text ?: "id=$id")
    }


}