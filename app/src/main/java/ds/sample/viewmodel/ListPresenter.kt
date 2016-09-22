package ds.sample.viewmodel

import android.databinding.ObservableField
import android.os.Bundle
import ds.prodigy.Presenter
import ds.prodigy.component.IComponent
import ds.sample.adapter.NamesAdapter
import ds.sample.data.Name

class ListPresenter : Presenter<IComponent>() {

    val adapter = ObservableField<NamesAdapter>()

    override fun onCreate(bundle: Bundle?) {
        adapter.set(NamesAdapter(component!!.getContext(), getData()))
    }

    private fun getData(): List<Name> {
        return arrayListOf(
            Name("Vasya", "Pupkin"),
            Name("Geralt", "of Rivia"),
            Name("John", "Rambo"),
            Name("Luke", "Skywalker"),
            Name("Pikachu", "the Pokemon"),
            Name("Sasha", "Grey"),
            Name("Harry", "Potter"),
            Name("Stepan", "Bandera"),
            Name("Shao", "Tsung"),
            Name("Eric", "Cartman"),
            Name("Ivo", "Bobul"),
            Name("Bruce", "Lee"),
            Name("Vasya", "Pupkin"),
            Name("Geralt", "of Rivia"),
            Name("John", "Rambo"),
            Name("Luke", "Skywalker"),
            Name("Pikachu", "the Pokemon"),
            Name("Sasha", "Grey"),
            Name("Harry", "Potter"),
            Name("Stepan", "Bandera"),
            Name("Shao", "Tsung"),
            Name("Eric", "Cartman"),
            Name("Ivo", "Bobul"),
            Name("Bruce", "Lee")
        )
    }

}

