package ds.sample.viewmodel

import android.databinding.ObservableField
import android.os.Bundle
import ds.prodigy.Config
import ds.prodigy.Presenter
import ds.prodigy.component.IComponent
import ds.sample.R
import ds.sample.util.L
import ds.sample.view.EmptyActivity

@Config(component = EmptyActivity::class, layout = R.layout.activity_test2)
class TestPresenter2(val name: String, val text: String) : Presenter<IComponent>() {

    val label1 = ObservableField<String>()
    val label2 = ObservableField<String>()

    override fun onCreate(bundle: Bundle?) {
        L.v("test2 created")
        label1.set(name)
        label2.set(text)
    }

    fun onBtnClick() {
        val main = MainPresenter()
        navigator.run(main)
    }

}