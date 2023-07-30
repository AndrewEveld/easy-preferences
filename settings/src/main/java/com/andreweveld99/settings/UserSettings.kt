package com.andreweveld99.settings

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Build
import androidx.annotation.RequiresApi
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy

class UserSettings {
    companion object {
        @Suppress("UNCHECKED_CAST")
        inline fun <reified T> userSettingsFrom(context: Context, settingsClass: Class<T>): T {
            val sharedPreferences = context.getSharedPreferences(settingsClass.name, MODE_PRIVATE)

            return Proxy.newProxyInstance(settingsClass.classLoader,
                arrayOf(T::class.java), InvocationHandler { _, method, args ->
                    if (method.name.substring(0, 3) == "get") {
                        if (method.isAnnotationPresent(StringSetting::class.java)) {
                            val defaultValue =
                                method.getDeclaredAnnotation(StringSetting::class.java)!!.defaultValue
                            val key = method.name.substring(3)
                            val toReturn = sharedPreferences.getString(key, defaultValue)
                            return@InvocationHandler toReturn
                        }
                        if (method.isAnnotationPresent(BooleanSetting::class.java)) {
                            val defaultValue =
                                method.getDeclaredAnnotation(BooleanSetting::class.java)!!.defaultValue
                            val key = method.name.substring(3)
                            val toReturn = sharedPreferences.getBoolean(key, defaultValue)
                            return@InvocationHandler toReturn
                        }
                        if (method.isAnnotationPresent(IntSetting::class.java)) {
                            val defaultValue =
                                method.getDeclaredAnnotation(IntSetting::class.java)!!.defaultValue
                            val key = method.name.substring(3)
                            val toReturn = sharedPreferences.getInt(key, defaultValue)
                            return@InvocationHandler toReturn
                        }
                        if (method.isAnnotationPresent(LongSetting::class.java)) {
                            val defaultValue =
                                method.getDeclaredAnnotation(LongSetting::class.java)!!.defaultValue
                            val key = method.name.substring(3)
                            val toReturn = sharedPreferences.getLong(key, defaultValue)
                            return@InvocationHandler toReturn
                        }
                        if (method.isAnnotationPresent(FloatSetting::class.java)) {
                            val defaultValue =
                                method.getDeclaredAnnotation(FloatSetting::class.java)!!.defaultValue
                            val key = method.name.substring(3)
                            val toReturn = sharedPreferences.getFloat(key, defaultValue)
                            return@InvocationHandler toReturn
                        }
                    }

                    if (method.name.substring(0, 3) == "set") {
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
                        }
                    }
                    return@InvocationHandler null
                }
            ) as T
        }
    }
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