package ds.sample.viewmodel

import ds.salo.Presenter
import ds.sample.util.L

class CallbackPresenter() : Presenter() {

    override fun onCreate() {
        L.v("CallbackPresenter created")
    }

    override fun onAttach() {
        L.v("CallbackPresenter attached")
    }

    override fun onDetach() {
        L.v("CallbackPresenter detached")
    }

    override fun onDestroy() {
        L.v("CallbackPresenter destroyed")
    }

    fun onBtnClick() {
        //onResult?.invoke("awesome result!")
        L.v("clicked")
        setResult("awesome result!")
        finish()
    }



}