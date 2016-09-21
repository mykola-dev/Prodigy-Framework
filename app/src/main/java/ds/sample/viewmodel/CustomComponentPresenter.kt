package ds.sample.viewmodel

import android.os.Bundle
import ds.prodigy.Presenter
import ds.sample.view.CustomComponent

class CustomComponentPresenter : Presenter<CustomComponent>() {

    override fun onCreate(bundle: Bundle?) {
        component?.showToast("hello custom!")

        // attach some fragment. thats it. 1 line of code
        navigator.run(SimpleFragmentPresenter("here is fragment"))
    }


}

