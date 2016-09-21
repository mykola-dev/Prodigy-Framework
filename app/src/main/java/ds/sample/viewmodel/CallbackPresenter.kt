package ds.sample.viewmodel

import ds.prodigy.IComponent
import ds.prodigy.Presenter

class CallbackPresenter() : Presenter<IComponent>() {

    fun onBtnClick() {
        setResult("awesome result!")
        finish()
    }

}