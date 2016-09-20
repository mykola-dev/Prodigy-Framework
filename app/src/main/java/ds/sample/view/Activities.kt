package ds.sample.view

import android.view.Menu
import ds.salo.BindingActivity
import ds.sample.R
import ds.sample.util.toast

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

class TestActivity2 : BindingActivity()
class CallbackActivity : BindingActivity()
class ViewPagerActivity : BindingActivity()
class CustomComponentActivity : BindingActivity(), CustomComponent {
    override fun showToast(text: String) {
        toast(text)
    }
}