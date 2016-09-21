package ds.prodigy

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem

abstract class BindingDialogFragment : DialogFragment(), IComponent, DialogInterface.OnClickListener {

    data class ButtonData(val title: String, val action: Int)

    override val delegate = BinderDelegate()
    override val binding: ViewDataBinding by lazy { delegate.binding!! }

    override fun invalidateOptionsMenu() {
        activity.invalidateOptionsMenu()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        delegate.onCreate(this, savedInstanceState)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        delegate.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        delegate.onPause(this)
    }

    override fun onDestroyView() {
        println("fragment onDestroyView")
        delegate.onDestroy(this)
        super.onDestroyView()
    }

   /* override fun onDestroy() {
        println("fragment onDestroy")
        delegate.onDestroy(this)
        super.onDestroy()
    }*/

    override fun onSaveInstanceState(outState: Bundle) {
        delegate.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun getContext(): Context {
        return activity.applicationContext
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        delegate.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return delegate.onOptionsItemSelected(item)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val b = getDialogBuilder()

        // setup buttons
        getDialogButtons().forEach {
            if (it.action == DialogInterface.BUTTON_POSITIVE)
                b.setPositiveButton(it.title, this)
            else if (it.action == DialogInterface.BUTTON_NEGATIVE)
                b.setNegativeButton(it.title, this)
            else if (it.action == DialogInterface.BUTTON_NEUTRAL)
                b.setNeutralButton(it.title, this)
        }

        return b.create()
    }

    override fun onClick(dialog: DialogInterface, which: Int) {
        delegate.onDialogClick(dialog, which)
    }

    protected fun getDialogBuilder(): AlertDialog.Builder = AlertDialog.Builder(activity).setView(binding.root)

    abstract protected fun getDialogButtons(): List<ButtonData>
}