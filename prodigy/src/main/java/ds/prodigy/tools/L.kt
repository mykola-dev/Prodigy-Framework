package ds.prodigy.tools

import android.util.Log

object L {

    var logEnabled = true

    fun v(tag: String, message: String) {
        if (logEnabled) Log.v(tag, message)
    }

    fun d(tag: String, message: String) {
        if (logEnabled) Log.d(tag, message)
    }

    fun i(tag: String, message: String) {
        if (logEnabled) Log.i(tag, message)
    }

    fun w(tag: String, message: String) {
        if (logEnabled) Log.w(tag, message)
    }

    fun e(tag: String, message: String) {
        if (logEnabled) Log.e(tag, message)
    }
}