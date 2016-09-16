package ds.sample.viewmodel

import android.databinding.ObservableField
import ds.salo.Presenter
import ds.sample.util.L

class DialogPresenter() : Presenter() {

    val editField = ObservableField<String>()

    //var onResult: ((String) -> Unit)? = null

    override fun onCreate() {
        L.v("DialogPresenter created")
        navigator.onDialogButton = {
            L.v("set result...")
            setResult(it)
        }
    }

    override fun onAttach() {
        L.v("DialogPresenter attached")
    }

    override fun onDetach() {
        L.v("DialogPresenter detached")
    }

    override fun onDestroy() {
        L.v("DialogPresenter destroyed")
    }


}