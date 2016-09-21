package ds.sample.viewmodel

import android.databinding.ObservableField
import android.os.Bundle
import ds.prodigy.IComponent
import ds.prodigy.Presenter

class ToolBarPresenter : Presenter<IComponent>() {

    // views
    val toolbarTitle = ObservableField<String>("This is Toolbar!")

    override fun onCreate(bundle: Bundle?) {
        if (justCreated) {
            navigator.run(ButtonFragmentPresenter())
        }
    }


}