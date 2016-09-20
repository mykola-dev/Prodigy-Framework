package ds.sample.viewmodel

import android.databinding.ObservableField
import android.view.MenuItem
import ds.salo.IComponent
import ds.salo.Presenter
import ds.salo.respectLifeCycle
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

    val generator = Observable.just("AWESOME DATA", "another data", "latest data")
        .zipWith(Observable.interval(1, TimeUnit.SECONDS), { d, t -> d })

    override fun onCreate() {
        add("[created]")
    }

    override fun onAttach() {
        add("[attached]")
        // lifecycle test
    }

    override fun onDetach() {
        add("[detached]")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_next -> {
                generator
                    .doOnNext { L.v(it) }
                    .applySchedulers()
                    .respectLifeCycle(this)
                    .subscribe {
                        L.v("isAttached=${isAttached()} data=$it")
                        add(it)
                    }
                navigator.runComponent(TestActivity::class.java)
            }
        }
        return true
    }

    fun add(t: String) = text.set("${text.get()}${SimpleDateFormat("hh:mm:ss:SSS").format(Date())}: $t\n")


}