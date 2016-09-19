package ds.salo

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

abstract class BindingActivity : AppCompatActivity(), IComponent {

    override val delegate = BinderDelegate()
    override val binding by lazy { delegate.binding!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        super.onPause()
        delegate.onPause(this)
    }

    override fun onStop() {
        super.onStop()
        delegate.onStop(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        delegate.onDestroy(this)
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

