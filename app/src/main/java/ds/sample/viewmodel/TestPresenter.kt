package ds.sample.viewmodel

import ds.salo.Presenter
import ds.sample.util.L

class TestPresenter : Presenter() {

    override fun onCreate() {
        L.v("test created")
    }

    override fun onAttach() {
        L.v("test attached")
    }

    override fun onDetach() {
        L.v("test detached")
    }

    override fun onDestroy() {
        L.v("test destroyed")
    }


}