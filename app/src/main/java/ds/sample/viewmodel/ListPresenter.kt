package ds.sample.viewmodel

import android.databinding.ObservableField
import ds.salo.Presenter
import ds.sample.adapter.NamesAdapter
import ds.sample.data.Name
import ds.sample.util.L

class ListPresenter : Presenter() {

    val adapter = ObservableField<NamesAdapter>()

    override fun onCreate() {
        L.v("ListPresenter created")
        adapter.set(NamesAdapter(component!!.getContext(), getData()))
    }

    override fun onAttach() {
        L.v("ListPresenter attached")
    }

    override fun onDetach() {
        L.v("ListPresenter detached")
    }

    override fun onDestroy() {
        L.v("ListPresenter destroyed")
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

