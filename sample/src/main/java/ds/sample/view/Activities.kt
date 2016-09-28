package ds.sample.view

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import ds.prodigy.component.BindingActivity
import ds.sample.R
import ds.sample.util.toast

class EmptyActivity : BindingActivity()
class ViewPagerActivity : BindingActivity()

class MainActivity : BindingActivity(), MenuComponent {
    override fun toggleNotificationsIcon(menu: Menu, enable: Boolean) {
        with(menu.findItem(R.id.menu_notifications)) {
            if (enable) setIcon(R.drawable.ic_notifications)
            else setIcon(R.drawable.ic_notifications_off)
        }
    }

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

class ToolBarActivity : BindingActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.root.findViewById(R.id.toolbar) as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
