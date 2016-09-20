package ds.sample.viewmodel

import android.databinding.ObservableField
import ds.salo.IComponent
import ds.salo.Presenter
import ds.sample.adapter.NamesAdapter
import ds.sample.data.Name

class ListPresenter : Presenter<IComponent>() {

    val adapter = ObservableField<NamesAdapter>()

    override fun onCreate() {
        super.onCreate()
        adapter.set(NamesAdapter(component!!.getContext(), getData()))
    }

    private fun getData(): List<Name> {
        return arrayListOf(
            Name("Vasya", "Pupkin"),
            Name("Geralt", "of Rivia"),
            Name("John", "Rambo"),
            Name("Luke", "Skywalker"),
            Name("Pikachu", "the Pokemon"),
            Name("Vasya", "Pupkin"),
            Name("Geralt", "of Rivia"),
            Name("John", "Rambo"),
            Name("Luke", "Skywalker"),
            Name("Pikachu", "the Pokemon")
        )
    }

}

