package ds.sample.viewmodel

import ds.salo.Presenter
import ds.sample.view.CustomComponent

class CustomComponentPresenter : Presenter<CustomComponent>() {

    override fun onCreate() {
        super.onCreate()
        component?.showToast("hello custom!")

        // attach some fragment. thats it. 1 line of code
        navigator.run(SimpleFragmentPresenter("here is fragment"))
    }


}

