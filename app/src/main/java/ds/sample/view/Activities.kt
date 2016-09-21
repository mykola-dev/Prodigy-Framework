package ds.sample.view

import android.view.Menu
import ds.prodigy.BindingActivity
import ds.sample.R
import ds.sample.util.toast

class EmptyActivity : BindingActivity()
class ViewPagerActivity : BindingActivity()

class MainActivity : BindingActivity() {
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }
}

class TestActivity : BindingActivity() {
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.test, menu)
        return super.onCreateOptionsMenu(menu)
    }
}

class CustomComponentActivity : BindingActivity(), CustomComponent {
    override fun showToast(text: String) {
        toast(text)
    }
}

class ToolBarActivity : BindingActivity(){

}

