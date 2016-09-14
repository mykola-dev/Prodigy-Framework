package ds.salo

import android.app.Activity
import android.content.Intent
import android.os.Bundle

class Navigator(val presenter: Presenter) {

    /**
     * Run Activity
     */
    fun startActivity(activityClass: Class<out BindingActivity>, extras: Bundle? = null) {
        println("startActivity ${activityClass.name}")
        val a = presenter.getActivity()
        if (a != null) {
            val intent = Intent(a, activityClass)
            if (extras != null)
                intent.putExtras(extras)
            a.startActivity(intent)
        } else
            throw IllegalStateException("activity is null")
    }

    fun goto(cls: Class<out BindingAware>, bundle: Bundle? = null) {
        println("goto ${cls.name} ${if (bundle != null) "with bundle" else ""}")
        if (Activity::class.java.isAssignableFrom(cls)) {
            startActivity(cls as Class<out BindingActivity>, bundle)
        } else {
            throw IllegalStateException("${cls.name} doesnt support yet")
            // todo fragments/views
        }
    }
}