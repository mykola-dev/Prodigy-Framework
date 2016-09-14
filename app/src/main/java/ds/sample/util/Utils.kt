package ds.sample.util

import android.os.Looper

object Utils {
    val isUiThread: Boolean
        get() = Thread.currentThread() === Looper.getMainLooper().thread


    fun logThread() {
        L.i("UI thread? %s", isUiThread)
    }


}
