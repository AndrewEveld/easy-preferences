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

    // First, we make sure each method in interface is supported by Easy Preferences.
    easyPrefsInterface.declaredMethods.forEach { method ->
        val doesEPSupportMethod = IsMethodSupportedUseCase().execute(method)
        if (!doesEPSupportMethod) {
            throw InvalidMethodException()
        }
    }

    // Then, we can create or fetch the shared preferences file.
    val sharedPreferences: SharedPreferences =
        this.getSharedPreferences(easyPrefsInterface.name, MODE_PRIVATE)

    // then we return a proxy instance that implements getters and setters for the preferences
    // variables.
    return Proxy.newProxyInstance(easyPrefsInterface.classLoader,
        arrayOf(easyPrefsInterface), InvocationHandler { _, method, args ->
            if (method.name == "toString") return@InvocationHandler "User Setting: ${easyPrefsInterface.name}"
            if (method.name.substring(0, 3) == "get") {
                return@InvocationHandler HandleGetPreferenceUseCase(sharedPreferences).execute(method)
            } else if (method.name.substring(0, 3) == "set") {
                HandleSetPreferenceUseCase(sharedPreferences).execute(method, args)
                return@InvocationHandler null
            }
            throw InvalidMethodException()
        }
    ) as T
}

class InvalidMethodException : Exception("An invalid method was detected.")
class PropertyHasUnsupportedType(propertyName: String) :
    Exception("The type of $propertyName is not valid.")