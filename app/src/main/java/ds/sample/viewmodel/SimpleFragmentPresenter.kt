package ds.sample.viewmodel

import android.databinding.ObservableField
import ds.prodigy.Presenter
import ds.prodigy.component.IComponent
import java.text.SimpleDateFormat
import java.util.*

class SimpleFragmentPresenter(text: String? = null) : Presenter<IComponent>() {
    val text = ObservableField<String>()
    val time = ObservableField<String>()

    init {
        this.text.set(text ?: "id=$id")
    }

    override fun onAttach() {
        val date = SimpleDateFormat.getDateTimeInstance().format(Date())
        time.set(date)
    }


}