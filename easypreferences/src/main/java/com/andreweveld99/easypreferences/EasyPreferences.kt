package com.andreweveld99.easypreferences

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy

/**
 * @param easyPrefsInterface The interface defining your preferences structure and default values. You
 * can only have one Easy Preferences file per interface [T].
 *
 * @throws [InvalidMethodException] if [easyPrefsInterface] contains a method not supported by Easy
 * Preferences.
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T> Context.getEasyPreferences(easyPrefsInterface: Class<T>): T {
    val sharedPreferences: SharedPreferences =
        this.getSharedPreferences(easyPrefsInterface.name, MODE_PRIVATE)
    easyPrefsInterface.declaredMethods.forEach { method ->
        val doesEPSupportMethod = IsMethodSupportedUseCase().execute(method)
        if (!doesEPSupportMethod) {
            throw InvalidMethodException()
        }
    }
    return Proxy.newProxyInstance(easyPrefsInterface.classLoader,
        arrayOf(easyPrefsInterface), InvocationHandler { _, method, args ->
            if (method.name == "toString") return@InvocationHandler "User Setting: ${easyPrefsInterface.name}"
            if (method.name.substring(0, 3) == "get") {
                if (method.isAnnotationPresent(StringSetting::class.java)) {
                    val defaultValue =
                        method.getDeclaredAnnotation(StringSetting::class.java)!!.defaultValue
                    val key = method.name.substring(3)
                    return@InvocationHandler sharedPreferences.getString(key, defaultValue)
                } else if (method.isAnnotationPresent(BooleanSetting::class.java)) {
                    val defaultValue =
                        method.getDeclaredAnnotation(BooleanSetting::class.java)!!.defaultValue
                    val key = method.name.substring(3)
                    return@InvocationHandler sharedPreferences.getBoolean(key, defaultValue)
                } else if (method.isAnnotationPresent(IntSetting::class.java)) {
                    val defaultValue =
                        method.getDeclaredAnnotation(IntSetting::class.java)!!.defaultValue
                    val key = method.name.substring(3)
                    return@InvocationHandler sharedPreferences.getInt(key, defaultValue)
                } else if (method.isAnnotationPresent(LongSetting::class.java)) {
                    val defaultValue =
                        method.getDeclaredAnnotation(LongSetting::class.java)!!.defaultValue
                    val key = method.name.substring(3)
                    return@InvocationHandler sharedPreferences.getLong(key, defaultValue)
                } else if (method.isAnnotationPresent(FloatSetting::class.java)) {
                    val defaultValue =
                        method.getDeclaredAnnotation(FloatSetting::class.java)!!.defaultValue
                    val key = method.name.substring(3)
                    return@InvocationHandler sharedPreferences.getFloat(key, defaultValue)
                } else {
                    throw PropertyHasUnsupportedType(method.name.substring(3))
                }
            } else if (method.name.substring(0, 3) == "set") {
                when (args[0]) {
                    is String -> {
                        val toPut = args[0] as String
                        val key = method.name.substring(3)
                        sharedPreferences.edit().putString(key, toPut)
                            .apply()
                        return@InvocationHandler null
                    }
                    is Int -> {
                        val toPut = args[0] as Int
                        val key = method.name.substring(3)
                        sharedPreferences.edit().putInt(key, toPut)
                            .apply()
                        return@InvocationHandler null
                    }
                    is Boolean -> {
                        val toPut = args[0] as Boolean
                        val key = method.name.substring(3)
                        sharedPreferences.edit().putBoolean(key, toPut)
                            .apply()
                        return@InvocationHandler null
                    }
                    is Long -> {
                        val toPut = args[0] as Long
                        val key = method.name.substring(3)
                        sharedPreferences.edit().putLong(key, toPut)
                            .apply()
                        return@InvocationHandler null
                    }
                    is Float -> {
                        val toPut = args[0] as Float
                        val key = method.name.substring(3)
                        sharedPreferences.edit().putFloat(key, toPut)
                            .apply()
                        return@InvocationHandler null
                    }
                    else -> {
                        throw PropertyHasUnsupportedType(method.name.substring(3))
                    }
                }
            }
            throw InvalidMethodException()
        }
    ) as T
}

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class StringSetting(val defaultValue: String)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class BooleanSetting(val defaultValue: Boolean)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class IntSetting(val defaultValue: Int)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class FloatSetting(val defaultValue: Float)

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY_GETTER)
annotation class LongSetting(val defaultValue: Long)

class InvalidMethodException : Exception("An invalid method was detected.")
class PropertyHasUnsupportedType(propertyName: String) :
    Exception("The type of $propertyName is not valid.")