package io.github.andreweveld.easypreferences

import android.content.SharedPreferences
import java.lang.reflect.Method

class HandleSetPreferenceUseCase(private val sharedPreferences: SharedPreferences) {
    fun execute(method: Method, arguments: Array<Any>) {
        when (arguments[0]) {
            is String -> {
                val toPut = arguments[0] as String
                val key = method.name.substring(3)
                sharedPreferences.edit().putString(key, toPut)
                    .apply()
            }
            is Int -> {
                val toPut = arguments[0] as Int
                val key = method.name.substring(3)
                sharedPreferences.edit().putInt(key, toPut)
                    .apply()
            }
            is Boolean -> {
                val toPut = arguments[0] as Boolean
                val key = method.name.substring(3)
                sharedPreferences.edit().putBoolean(key, toPut)
                    .apply()
            }
            is Long -> {
                val toPut = arguments[0] as Long
                val key = method.name.substring(3)
                sharedPreferences.edit().putLong(key, toPut)
                    .apply()
            }
            is Float -> {
                val toPut = arguments[0] as Float
                val key = method.name.substring(3)
                sharedPreferences.edit().putFloat(key, toPut)
                    .apply()
            }
            else -> {
                throw PropertyHasUnsupportedType(method.name.substring(3))
            }
        }
    }
}