package ds.sample.viewmodel

import android.databinding.ObservableField
import ds.salo.Presenter
import ds.sample.util.L

class TestPresenter2(val name: String, val text: String) : Presenter() {

    val label1 = ObservableField<String>()
    val label2 = ObservableField<String>()

    override fun onCreate() {
        L.v("test2 created")
        label1.set(name)
        label2.set(text)
    }

    override fun onAttach() {
        L.v("test2 attached")
    }

    override fun onDetach() {
        L.v("test2 detached")
    }

    override fun onDestroy() {
        L.v("test2 destroyed")
    }

    fun onBtnClick() {
        val main = MainPresenter()
        navigator.run(main)
    }

}