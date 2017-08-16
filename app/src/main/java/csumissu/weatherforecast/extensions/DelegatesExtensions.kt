package csumissu.weatherforecast.extensions

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

/**
 * @author yxsun
 * @since 07/06/2017
 */

object DelegatesExt {
    fun <T> notNullSingleValue() = NotNullSingleValueVar<T>()
    inline fun <reified T : Any> preference(context: Context, name: String, defaultValue: T? = null) =
            Preference(context, name, defaultValue, T::class)
}

class NotNullSingleValueVar<T> : ReadWriteProperty<Any?, T> {
    private var value: T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("${property.name} not initialized")
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = if (this.value == null) {
            value
        } else {
            throw IllegalStateException("${property.name} already initialized")
        }
    }
}

@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
class Preference<T : Any>(val context: Context,
                          val name: String,
                          private val defaultValue: T?,
                          private val clazz: KClass<T>) : ReadWriteProperty<Any?, T?> {
    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences("default", Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return prefs.run {
            when (clazz) {
                Int::class -> getInt(name, (defaultValue ?: 0) as Int)
                Long::class -> getLong(name, (defaultValue ?: 0L) as Long)
                Float::class -> getFloat(name, (defaultValue ?: 0F) as Float)
                Boolean::class -> getBoolean(name, (defaultValue ?: false) as Boolean)
                String::class -> getString(name, (defaultValue ?: "") as String)
                else -> throw IllegalArgumentException("This type $clazz can't be save into Preferences")
            }
        } as T
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        prefs.edit().apply {
            when (value) {
                is Int -> putInt(name, value)
                is Long -> putLong(name, value)
                is Float -> putFloat(name, value)
                is Boolean -> putBoolean(name, value)
                is String -> putString(name, value)
                null -> remove(name)
                else -> throw IllegalArgumentException("This value $value can't be saved into Preferences")
            }
        }.apply()
    }
}