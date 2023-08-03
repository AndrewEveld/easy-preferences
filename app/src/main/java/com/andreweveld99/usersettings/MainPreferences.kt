package com.andreweveld99.usersettings

import com.andreweveld99.settings.*

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