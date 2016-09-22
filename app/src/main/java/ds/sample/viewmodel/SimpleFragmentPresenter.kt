package ds.sample.viewmodel

import android.databinding.ObservableField
import ds.prodigy.Presenter
import ds.prodigy.component.IComponent

class SimpleFragmentPresenter(text: String? = null) : Presenter<IComponent>() {
    val text = ObservableField<String>()

    init {
        this.text.set(text ?: "id=$id")
    }


}