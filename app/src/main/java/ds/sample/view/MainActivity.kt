package ds.sample.view

import android.view.Menu
import ds.salo.BindingActivity
import ds.sample.R

class MainActivity : BindingActivity() {

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }
}

