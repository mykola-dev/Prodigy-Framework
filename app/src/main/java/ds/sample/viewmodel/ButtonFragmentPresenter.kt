package ds.sample.viewmodel

import android.databinding.ObservableField
import android.os.Bundle
import ds.prodigy.Presenter
import ds.prodigy.component.IComponent

class ButtonFragmentPresenter(val index: Int = 1) : Presenter<IComponent>() {

    val buttonText = ObservableField<String>("fragment $index")

    override fun onCreate(bundle: Bundle?) {
        setResult("returned from $index")
    }

    fun onNextButton() {
        val presenter = ButtonFragmentPresenter(index + 1)
        presenter.setCallback<String> { toast(it) }
        navigator.run(presenter, addToBackstack = true)
    }

    fun onFinishButton() {
        navigator.finish()
    }

}