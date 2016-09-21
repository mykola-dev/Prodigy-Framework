package ds.sample.viewmodel

import android.databinding.ObservableField
import android.view.MenuItem
import ds.prodigy.IComponent
import ds.prodigy.Presenter
import ds.prodigy.respectLifeCycle
import ds.sample.R
import ds.sample.util.L
import ds.sample.util.applySchedulers
import ds.sample.view.TestActivity
import rx.Observable
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TestPresenter : Presenter<IComponent>() {

    val text = ObservableField<String>("")

    val generator = Observable.range(1, 3)
        .zipWith(Observable.interval(1, TimeUnit.SECONDS), { d, t -> d })

    override fun onCreate() {
        add("[created]")
    }

    override fun onAttach() {
        add("[attached]")
    }

    override fun onDetach() {
        add("[detached]")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_next -> {
                generator
                    .doOnNext { L.v("isAttached=${isAttached()} AWESOME DATA $it") }
                    .applySchedulers()
                    .respectLifeCycle(this)
                    .subscribe {
                        add("AWESOME DATA $it")
                    }
                navigator.runComponent(TestActivity::class.java)
            }
        }
        return true
    }

    fun add(t: String) = text.set("${text.get()}${SimpleDateFormat("hh:mm:ss:SSS").format(Date())}: $t\n")


}