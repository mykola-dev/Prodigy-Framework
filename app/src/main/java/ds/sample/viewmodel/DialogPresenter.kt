package ds.sample.viewmodel

import android.databinding.ObservableField
import android.os.Bundle
import ds.prodigy.component.IComponent
import ds.prodigy.Presenter
import ds.sample.util.L

class DialogPresenter() : Presenter<IComponent>() {

    val editField = ObservableField<String>()

    override fun onCreate(bundle: Bundle?) {
        navigator.onDialogButton = {
            L.v("set result...")
            setResult(it)
        }
    }


}