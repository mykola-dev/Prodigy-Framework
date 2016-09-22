package ds.sample.viewmodel

import ds.prodigy.component.IComponent
import ds.prodigy.Presenter

class CallbackPresenter() : Presenter<IComponent>() {

    fun onBtnClick() {
        setResult("awesome result!")
        navigator.finish()
    }

}