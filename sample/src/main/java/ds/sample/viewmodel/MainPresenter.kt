package ds.sample.viewmodel

import android.content.DialogInterface.BUTTON_NEGATIVE
import android.content.DialogInterface.BUTTON_POSITIVE
import android.databinding.Bindable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import ds.prodigy.Config
import ds.prodigy.Presenter
import ds.prodigy.Prodigy
import ds.prodigy.component.IComponent
import ds.prodigy.tools.property
import ds.prodigy.tools.respectLifeCycle
import ds.sample.BR
import ds.sample.R
import ds.sample.util.L
import ds.sample.view.MainActivity
import ds.sample.view.TestActivity
import rx.Observable
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@Config(component = MainActivity::class, layout = R.layout.activity_main)
class MainPresenter : Presenter<IComponent>() {

    // views
    @get:Bindable var textField by property<String>(BR.textField)

    private var notificationsEnabled = true

    override fun onCreate(bundle: Bundle?) {
        Observable.interval(1, TimeUnit.SECONDS)
            .respectLifeCycle(this)
            .subscribe {
                textField = SimpleDateFormat.getDateTimeInstance().format(Date())
            }
    }


    fun onScreen1ButtonClick(v: View) {
        L.v("clicked!")
        navigator.runComponent(TestActivity::class.java)
    }

    fun onScreen2ButtonClick() {
        L.v("clicked!")
        val test2: TestPresenter2 = TestPresenter2("Marty McFly", textField!!)
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
                BUTTON_POSITIVE -> toast("ok pressed")
                BUTTON_NEGATIVE -> toast("cancel pressed")
            }
        }
        navigator.run(p)
    }

    fun onViewPagerDemo() {
        val p = ViewPagerPresenter()
        navigator.run(p)
    }

    fun onCustomComponent() {
        val p = CustomComponentPresenter()
        navigator.run(p)
    }

    fun onFragmentsTest() {
        navigator.run(ToolBarPresenter())
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
                component?.invalidateOptionsMenu()
            }
            R.id.menu_memory_diagnostic -> {
                toast(Prodigy.getDiagnostics())
            }
        }
        return true
    }


}