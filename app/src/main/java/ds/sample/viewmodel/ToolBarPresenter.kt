package ds.sample.viewmodel

import android.databinding.ObservableField
import android.os.Bundle
import ds.prodigy.Presenter
import ds.prodigy.component.IComponent

class ToolBarPresenter : Presenter<IComponent>() {

    // views
    val toolbarTitle = ObservableField<String>("This is Toolbar!")

    override fun onCreate(bundle: Bundle?) {
        if (justCreated) {
            navigator.run(ButtonFragmentPresenter())
        }
    }


}