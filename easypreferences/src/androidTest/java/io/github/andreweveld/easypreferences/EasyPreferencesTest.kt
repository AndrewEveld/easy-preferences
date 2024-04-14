package io.github.andreweveld.easypreferences

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.andreweveld.easypreferences.BooleanPreference
import io.github.andreweveld.easypreferences.FloatPreference
import io.github.andreweveld.easypreferences.IntPreference
import io.github.andreweveld.easypreferences.LongPreference
import io.github.andreweveld.easypreferences.StringPreference
import io.github.andreweveld.easypreferences.getEasyPreferences

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class EasyPreferencesTest {
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        context.deleteSharedPreferences(ForTesting::class.java.name)
    }

    @Test
    fun stringGetAndSetShouldWork() {
        val testing = context.getEasyPreferences(ForTesting::class.java)
        val default = testing.string
        testing.string = "poppyCock"
        val value = testing.string
        assert(value == "poppyCock")
        assert(default == "obiwankenobi")
    }

    @Test
    fun booleanGetAndSetShouldWork() {
        val testing = context.getEasyPreferences(ForTesting::class.java)
        val default = testing.boolean
        testing.boolean = true
        val value = testing.boolean

        assert(value)
        assert(!default)
    }

    @Test
    fun intGetAndSetShouldWork() {
        val testing = context.getEasyPreferences(ForTesting::class.java)
        val default = testing.int
        testing.int = 5
        val value = testing.int

        assert(value == 5)
        assert(default == 0)
    }

    @Test
    fun longGetAndSetShouldWork() {
        val testing = context.getEasyPreferences(ForTesting::class.java)
        val default = testing.long
        testing.long = 5L
        val value = testing.long

        assert(value == 5L)
        assert(default == 0L)
    }

    @Test
    fun floatGetAndSetShouldWork() {
        val testing = context.getEasyPreferences(ForTesting::class.java)
        val default = testing.float
        testing.float = 5.0F
        val value = testing.float

        assert(value == 5.0F)
        assert(default == 0.0F)
    }

    interface ForTesting {
        @get:StringPreference("obiwankenobi")
        var string: String

        @get:BooleanPreference(false)
        var boolean: Boolean

        @get:IntPreference(0)
        var int: Int

        @get:LongPreference(0L)
        var long: Long

        @get:FloatPreference(0.0F)
        var float: Float
    }

    interface InvalidMethods {

    }
}