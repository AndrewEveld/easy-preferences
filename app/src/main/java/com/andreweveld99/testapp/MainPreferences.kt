package com.andreweveld99.testapp

import com.andreweveld99.easypreferences.*

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