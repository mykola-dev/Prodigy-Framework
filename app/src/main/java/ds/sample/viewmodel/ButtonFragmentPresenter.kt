package ds.sample.viewmodel

import android.databinding.ObservableField
import ds.prodigy.IComponent
import ds.prodigy.Presenter

class ButtonFragmentPresenter(val index: Int = 1) : Presenter<IComponent>() {

    val buttonText = ObservableField<String>("fragment $index")

    fun onButton() {
        navigator.run(ButtonFragmentPresenter(index + 1), addToBackstack = true)
    }

}