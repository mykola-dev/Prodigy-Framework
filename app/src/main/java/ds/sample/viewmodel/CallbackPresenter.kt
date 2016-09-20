package ds.sample.viewmodel

import ds.salo.IComponent
import ds.salo.Presenter

class CallbackPresenter() : Presenter<IComponent>() {

    fun onBtnClick() {
        setResult("awesome result!")
        finish()
    }

}