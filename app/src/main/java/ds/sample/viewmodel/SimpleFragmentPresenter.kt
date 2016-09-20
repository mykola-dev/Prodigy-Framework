package ds.sample.viewmodel

import android.databinding.ObservableField
import ds.salo.IComponent
import ds.salo.Presenter

class SimpleFragmentPresenter(text: String? = null) : Presenter<IComponent>() {
    val text = ObservableField<String>()

    init {
        this.text.set(text ?: "id=$id")
    }


}