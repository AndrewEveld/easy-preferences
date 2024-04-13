package com.andreweveld99.easypreferences

import android.content.SharedPreferences
import java.lang.reflect.Method

class HandleGetPreferenceUseCase(private val sharedPreferences: SharedPreferences) {
    fun execute(method: Method): Any {
        if (method.isAnnotationPresent(StringPreference::class.java)) {
            val defaultValue =
                method.getDeclaredAnnotation(StringPreference::class.java)!!.defaultValue
            val key = method.name.substring(3)
            return sharedPreferences.getString(key, defaultValue) as Any
        } else if (method.isAnnotationPresent(BooleanPreference::class.java)) {
            val defaultValue =
                method.getDeclaredAnnotation(BooleanPreference::class.java)!!.defaultValue
            val key = method.name.substring(3)
            return sharedPreferences.getBoolean(key, defaultValue)
        } else if (method.isAnnotationPresent(IntPreference::class.java)) {
            val defaultValue =
                method.getDeclaredAnnotation(IntPreference::class.java)!!.defaultValue
            val key = method.name.substring(3)
            return sharedPreferences.getInt(key, defaultValue)
        } else if (method.isAnnotationPresent(LongPreference::class.java)) {
            val defaultValue =
                method.getDeclaredAnnotation(LongPreference::class.java)!!.defaultValue
            val key = method.name.substring(3)
            return sharedPreferences.getLong(key, defaultValue)
        } else if (method.isAnnotationPresent(FloatPreference::class.java)) {
            val defaultValue =
                method.getDeclaredAnnotation(FloatPreference::class.java)!!.defaultValue
            val key = method.name.substring(3)
            return sharedPreferences.getFloat(key, defaultValue)
        } else {
            throw PropertyHasUnsupportedType(method.name.substring(3))
        }
    }
}