package ds.prodigy.tools

import android.databinding.BaseObservable
import ds.prodigy.Presenter
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T> Presenter<*>.property(fieldId: Int, default: T? = null): ReadWriteProperty<Any, T?> = ObservableModelProperty(this, fieldId, default)

internal class ObservableModelProperty<T>(private val parent: BaseObservable, private val fieldId: Int, default: T?) : ReadWriteProperty<Any, T?> {
    private var current = default

    override fun getValue(thisRef: Any, property: KProperty<*>): T? {
        return current
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        if (current != value) {
            current = value
            parent.notifyPropertyChanged(fieldId)
        }
    }
}
