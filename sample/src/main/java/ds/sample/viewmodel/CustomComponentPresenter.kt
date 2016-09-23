package ds.sample.viewmodel

import android.os.Bundle
import ds.prodigy.Config
import ds.prodigy.Presenter
import ds.sample.R
import ds.sample.view.CustomComponent
import ds.sample.view.CustomComponentActivity

@Config(component = CustomComponentActivity::class, layout = R.layout.activity_custom)
class CustomComponentPresenter : Presenter<CustomComponent>() {

    override fun onCreate(bundle: Bundle?) {
        component?.showToast("hello custom!")

        // attach some fragment. thats it. 1 line of code
        navigator.run(SimpleFragmentPresenter("here is fragment"))
    }


}

