package ds.sample.viewmodel

import android.databinding.ObservableField
import ds.salo.IComponent
import ds.salo.Presenter
import ds.sample.util.L

class TestPresenter2(val name: String, val text: String) : Presenter<IComponent>() {

    val label1 = ObservableField<String>()
    val label2 = ObservableField<String>()

    override fun onCreate() {

        L.v("test2 created")
        label1.set(name)
        label2.set(text)
    }

    fun onBtnClick() {
        val main = MainPresenter()
        navigator.run(main)
    }

}