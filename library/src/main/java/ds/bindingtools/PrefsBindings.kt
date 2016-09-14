package ds.bindingtools

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

inline fun <reified T : Any> pref(default: T?): PrefDelegate<T> = PrefDelegate(default, T::class)

class PrefDelegate<T : Any>(val default: T?, val cls: KClass<T>) : ReadWriteProperty<Any?, T?> {

	companion object {
		lateinit var prefs: SharedPreferences
		fun init(ctx: Context, name: String) {
			prefs = ctx.getSharedPreferences(name, Context.MODE_PRIVATE)
		}
	}

	override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
		return prefs.getGeneric(property.name, default, cls)
	}

	override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
		prefs.putGeneric(property.name, value)
	}

}

@Suppress("unchecked_cast")
fun <T : Any> SharedPreferences.putGeneric(key: String, value: T?) {
	with(edit()) {
		when (value) {
			is String -> putString(key, value)
			is Int -> putInt(key, value)
			is Long -> putLong(key, value)
			is Float -> putFloat(key, value)
			is Boolean -> putBoolean(key, value)
			is Set<*> -> putStringSet(key, value as Set<String>)
			else -> throw IllegalArgumentException()
		}
		apply()
	}
}

@Suppress("unchecked_cast")
fun <T : Any> SharedPreferences.getGeneric(key: String, default: T?, cls: KClass<T>): T? {
	return when (cls) {
		String::class -> getString(key, default as String?) as T?
		java.lang.Integer::class -> getInt(key, default as Int) as T?
		java.lang.Long::class -> getLong(key, default as Long) as T?
		java.lang.Float::class -> getFloat(key, default as Float) as T?
		java.lang.Boolean::class -> getBoolean(key, default as Boolean) as T?
		Set::class -> getStringSet(key, default as Set<String>) as T?
		else -> throw IllegalArgumentException()
	}
}

