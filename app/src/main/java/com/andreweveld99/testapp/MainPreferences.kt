package com.andreweveld99.testapp

import com.andreweveld99.easypreferences.*

interface MainPreferences {
    @get:StringSetting("Default String")
    var string: String

    @get:BooleanSetting(false)
    var boolean: Boolean

    @get:IntSetting(0)
    var int: Int

    @get:LongSetting(0L)
    var long: Long

    @get:FloatSetting(0F)
    var float: Float
}