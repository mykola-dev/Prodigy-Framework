package ds.sample.viewmodel

import ds.prodigy.Config
import ds.prodigy.Presenter
import ds.prodigy.component.IComponent
import ds.sample.R
import ds.sample.view.EmptyActivity

@Config(component = EmptyActivity::class, layout = R.layout.activity_with_callback)
class CallbackPresenter() : Presenter<IComponent>() {

    fun onBtnClick() {
        setResult("awesome result!")
        navigator.finish()
    }

}