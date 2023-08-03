package com.andreweveld99.testapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import com.andreweveld99.easypreferences.userSettingsFrom

class MainActivity : AppCompatActivity() {
    private lateinit var settings: MainPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settings = userSettingsFrom(applicationContext, MainPreferences::class.java)
        setSettingsContent(settings)
    }

    @Composable
    fun Screen(settings: MainPreferences) {
        Scaffold {
            Column(modifier = Modifier.fillMaxSize()) {
                var string by rememberSaveable {
                    mutableStateOf("")
                }
                var boolean by rememberSaveable {
                    mutableStateOf(false)
                }
                var int by rememberSaveable {
                    mutableStateOf("")
                }
                var long by rememberSaveable {
                    mutableStateOf("")
                }
                var float by rememberSaveable {
                    mutableStateOf("")
                }
                Text(text = "String: ${settings.string}")
                Row {
                    TextField(
                        value = string,
                        modifier = Modifier.width(Dp(150F)),
                        onValueChange = { string = it })
                    Button(onClick = { settings.string = string }) {
                        Text("Update String")
                    }
                }
                Text(text = "Boolean: ${settings.boolean}")
                Row {
                    Switch(boolean, onCheckedChange = { boolean = it })
                    Button(onClick = { settings.boolean = boolean }) {
                        Text("Update Boolean")
                    }
                }
                Text(text = "Int: ${settings.int}")
                Row {
                    TextField(
                        value = int,
                        modifier = Modifier.width(Dp(150F)),
                        onValueChange = { int = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Button(onClick = {
                        try {
                            settings.int = int.toInt()
                        } catch (e: NumberFormatException) {
                            Log.e("NumberFormatException", e.toString())
                        }
                    }) {
                        Text("Update Int")
                    }
                }
                Text(text = "Long: ${settings.long}")
                Row {
                    TextField(
                        value = long,
                        modifier = Modifier.width(Dp(150F)),
                        onValueChange = { long = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Button(onClick = {
                        try {
                            settings.long = long.toLong()
                        } catch (e: NumberFormatException) {
                            Log.e("NumberFormatException", e.toString())
                        }
                    }) {
                        Text("Update Long")
                    }
                }
                Text(text = "Float: ${settings.float}")
                Row {
                    TextField(
                        value = float,
                        modifier = Modifier.width(Dp(150F)),
                        onValueChange = { float = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                    Button(onClick = {
                        try {
                            settings.float = float.toFloat()
                        } catch (e: NumberFormatException) {
                            Log.e("NumberFormatException", e.toString())
                        }
                    }) {
                        Text("Update Float")
                    }
                }
                Button(onClick = { setSettingsContent(settings) }) {
                    Text("Refresh Screen")
                }
                Text("Refreshing the screen will force the labels to fetch the updated values " +
                        "from the MainPreferences object. Updating values will only update them in " +
                        "the SharedPreferences file that MainPreferences is accessing.")
            }
        }
    }

    private fun setSettingsContent(settings: MainPreferences) {
        setContent {
            Screen(settings)
        }
    }

    @Preview
    @Composable
    fun ScreenPreview() {
        Screen(settings = object : MainPreferences {
            override var string: String
                get() = "placeholder"
                set(value) {}
            override var boolean: Boolean
                get() = true
                set(value) {}
            override var int: Int
                get() = 129
                set(value) {}
            override var long: Long
                get() = 7493893834930493L
                set(value) {}
            override var float: Float
                get() = 7.039382F
                set(value) {}
        })
    }
}