package io.github.andreweveld.testapp

import io.github.andreweveld.easypreferences.BooleanPreference
import io.github.andreweveld.easypreferences.FloatPreference
import io.github.andreweveld.easypreferences.IntPreference
import io.github.andreweveld.easypreferences.LongPreference
import io.github.andreweveld.easypreferences.StringPreference

interface MainPreferences {
    @get:StringPreference("Default String")
    var string: String

    @get:BooleanPreference(false)
    var boolean: Boolean

    @get:IntPreference(0)
    var int: Int

    @get:LongPreference(0L)
    var long: Long

    @get:FloatPreference(0F)
    var float: Float
}