package ds.sample.viewmodel

import android.databinding.ObservableField
import android.os.Bundle
import ds.prodigy.Config
import ds.prodigy.Presenter
import ds.prodigy.component.IComponent
import ds.sample.R
import ds.sample.util.L
import ds.sample.view.InputDialogFragment

@Config(component = InputDialogFragment::class, layout = R.layout.dialog_input)
class DialogPresenter() : Presenter<IComponent>() {

    val editField = ObservableField<String>()

    override fun onCreate(bundle: Bundle?) {
        navigator.onDialogButton = {
            L.v("set result...")
            setResult(it)
        }
    }


}