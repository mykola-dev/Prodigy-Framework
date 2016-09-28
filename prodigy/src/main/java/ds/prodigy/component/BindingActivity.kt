package ds.prodigy.component

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import ds.prodigy.BinderDelegate
import ds.prodigy.tools.L

abstract class BindingActivity : AppCompatActivity(), IComponent {
    val TAG = "A"

    override val delegate = BinderDelegate()
    override val binding by lazy { delegate.binding!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        L.v(TAG, "onCreate")
        delegate.onCreate(this, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        delegate.onStart(this)
    }

    override fun onResume() {
        super.onResume()
        delegate.onResume(this)
    }

    override fun onPause() {
        delegate.onPause(this)
        super.onPause()
    }

    override fun onStop() {
        delegate.onStop(this)
        super.onStop()
    }

    override fun onDestroy() {
        L.v(TAG, "onDestroy")
        delegate.onDestroy(this)
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        delegate.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        return delegate.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return delegate.onOptionsItemSelected(item)
    }

    override fun getContext(): Context = applicationContext
    override fun getActivity(): Activity = this


}

