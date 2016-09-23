package ds.sample.util

import android.content.Context
import android.util.TypedValue
import android.widget.Toast
import ds.sample.App
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

val Context.app: App
    get() = this.applicationContext as App

fun Context.toast(text: CharSequence): Unit = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

fun Context.dp(dips: Float): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dips, resources.displayMetrics).toInt()

fun <T> Observable<T>.applySchedulers(): Observable<T> = compose { it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }