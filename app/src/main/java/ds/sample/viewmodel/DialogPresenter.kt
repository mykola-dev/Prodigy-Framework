package ds.sample.viewmodel

import android.databinding.ObservableField
import ds.salo.IComponent
import ds.salo.Presenter
import ds.sample.util.L

class DialogPresenter() : Presenter<IComponent>() {

    val editField = ObservableField<String>()

    override fun onCreate() {
        super.onCreate()
        navigator.onDialogButton = {
            L.v("set result...")
            setResult(it)
        }
    }


}