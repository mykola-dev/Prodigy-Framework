package ds.sample.viewmodel

import android.databinding.ObservableField
import android.view.Menu
import android.view.MenuItem
import android.view.View
import ds.salo.Presenter
import ds.sample.R
import ds.sample.activity.TestActivity
import ds.sample.util.L
import java.text.SimpleDateFormat
import java.util.*

class MainPresenter : Presenter() {

    // views
    val textField = ObservableField<String>()

    private var notificationsEnabled = true

    override fun onCreate() {
        L.v("main presenter created")
        val date = SimpleDateFormat.getDateTimeInstance().format(Date())
        textField.set(date)
    }

    override fun onAttach() {
        L.v("main presenter attached")
    }

    override fun onDetach() {
        L.v("main presenter detached")
    }

    override fun onDestroy() {
        L.v("main presenter destroyed")
    }

    fun onScreen1ButtonClick(v: View) {
        L.v("clicked!")
        navigator.goto(TestActivity::class.java)
    }

    fun onScreen2ButtonClick() {
        L.v("clicked!")
        val test2: TestPresenter2 = TestPresenter2("Marty McFly", textField.get())
        run(test2)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        // todo get rid of view stuff in the presenter
        menu.findItem(R.id.menu_notifications)
            .setIcon(if (notificationsEnabled) R.drawable.ic_notifications else R.drawable.ic_notifications_off)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        L.v("item selected")
        when (item.itemId) {
            R.id.menu_notifications -> {
                notificationsEnabled = !notificationsEnabled
                bindingAware?.invalidateOptionsMenu()
            }
        //R.id.menu_settings -> navigator.goto()
        }
        return true
    }


}