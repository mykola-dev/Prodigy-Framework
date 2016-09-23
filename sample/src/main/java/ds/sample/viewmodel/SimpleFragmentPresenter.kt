package ds.sample.viewmodel

import android.databinding.ObservableField
import ds.prodigy.Config
import ds.prodigy.Presenter
import ds.prodigy.component.IComponent
import ds.sample.R
import ds.sample.view.SimpleFragment
import java.text.SimpleDateFormat
import java.util.*

@Config(component = SimpleFragment::class, layout = R.layout.fragment_simple)
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