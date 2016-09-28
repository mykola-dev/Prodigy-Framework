package ds.sample.viewmodel

import android.databinding.ObservableField
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.MenuItem
import ds.prodigy.Config
import ds.prodigy.Presenter
import ds.prodigy.component.IComponent
import ds.sample.R
import ds.sample.view.ToolBarActivity

@Config(component = ToolBarActivity::class, layout = R.layout.activity_toolbar)
class ToolBarPresenter : Presenter<IComponent>() {

    // views
    val toolbarTitle = ObservableField<String>("This is Toolbar!")

    override fun onCreate(bundle: Bundle?) {
        if (justCreated) {
            val presenter = ButtonFragmentPresenter()
            presenter.setCallback<String>(this) {
                toast("activity got: $it")
            }
            navigator.run(presenter)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> NavUtils.navigateUpFromSameTask(component?.getActivity())
        }
        return true
    }


}