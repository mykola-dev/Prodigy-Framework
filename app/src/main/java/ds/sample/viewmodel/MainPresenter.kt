package ds.sample.viewmodel

import android.content.DialogInterface
import android.databinding.ObservableField
import android.view.Menu
import android.view.MenuItem
import android.view.View
import ds.salo.Presenter
import ds.sample.R
import ds.sample.util.L
import ds.sample.view.TestActivity
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
        navigator.run(test2)
    }

    fun onScreen3ButtonClick() {
        L.v("clicked!")
        val p = CallbackPresenter()
        p.setCallback<String> {
            L.v("callback!")
            toast(it)
        }
        navigator.run(p)
    }

    fun onInputButtonClick() {
        val p = DialogPresenter()
        p.setCallback<Int> {
            L.v("callbacked!")
            when (it) {
                DialogInterface.BUTTON_POSITIVE -> toast("ok pressed")
                DialogInterface.BUTTON_NEGATIVE -> toast("cancel pressed")
            }
        }
        navigator.run(p)
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