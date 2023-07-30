package com.andreweveld99.settings

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@RunWith(AndroidJUnit4::class)
class UserSettingsTest {
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        context.deleteSharedPreferences(ForTesting::class.java.name)
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun stringTypeShouldWork() {
        val oldPrefs = context.getSharedPreferences("poppy", Context.MODE_PRIVATE)
        val testing = UserSettings.userSettingsFrom(context, ForTesting::class.java)
        val new = measureTime {
            val default = testing.string
            testing.string = "poppyCock"
            val value = testing.string
            assert(value == "poppyCock")
            assert(default == "obiwankenobi")
        }
         val old = measureTime {
             val default = oldPrefs.getString("hi", "default")
             oldPrefs.edit().putString("hi", "newstuff").commit()
             val value = oldPrefs.getString("hi", "default")
             assert(value == "newstuff")
             assert(default == "default")
         }
        context.deleteSharedPreferences("poppy")
        assert(new.inWholeMilliseconds > old.inWholeMilliseconds)


    }

    @Test
    fun booleanTypeShouldWork() {
        val testing = UserSettings.userSettingsFrom(context, ForTesting::class.java)
        val default = testing.boolean
        testing.boolean = true
        val value = testing.boolean

        assert(value)
        assert(!default)
    }

    @Test
    fun intTypeShouldWork() {
        val testing = UserSettings.userSettingsFrom(context, ForTesting::class.java)
        val default = testing.int
        testing.int = 5
        val value = testing.int

        assert(value == 5)
        assert(default == 0)
    }

    @Test
    fun longTypeShouldWork() {
        val testing = UserSettings.userSettingsFrom(context, ForTesting::class.java)
        val default = testing.long
        testing.long = 5L
        val value = testing.long

        assert(value == 5L)
        assert(default == 0L)
    }

    @Test
    fun floatTypeShouldWork() {
        val testing = UserSettings.userSettingsFrom(context, ForTesting::class.java)
        val default = testing.float
        testing.float = 5.0F
        val value = testing.float

        assert(value == 5.0F)
        assert(default == 0.0F)
    }

    interface ForTesting {
        @get:StringSetting("obiwankenobi")
        var string: String

        @get:BooleanSetting(false)
        var boolean: Boolean

        @get:IntSetting(0)
        var int: Int

        @get:LongSetting(0L)
        var long: Long

        @get:FloatSetting(0.0F)
        var float: Float
    }
}