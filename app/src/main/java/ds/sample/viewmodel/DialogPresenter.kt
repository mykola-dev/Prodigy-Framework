package ds.sample.viewmodel

import android.databinding.ObservableField
import ds.prodigy.IComponent
import ds.prodigy.Presenter
import ds.sample.util.L

class DialogPresenter() : Presenter<IComponent>() {

    val editField = ObservableField<String>()

    override fun onCreate() {
        navigator.onDialogButton = {
            L.v("set result...")
            setResult(it)
        }
    }


}