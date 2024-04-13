# easy-preferences

A Kotlin Android API that abstracts access to Android Shared Preferences.

## Usage

First you must define an interface with the values you wish to persist in the device's Shared
Preferences. The properties must be `var` and must be one of the following generic types: `String`
, `Int`, `Boolean`, `Long`, or `Float`. Each type has a corresponding annotation that should be
attached to the getter of the property.
Eg. `@get:StringSetting("this string is this property's default value")`.

```kotlin
import com.andreweveld99.settings.*

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
```

Once you have properly defined your interface you can use this function to create an instance of
your class that accesses
SharedPreferences: `userSettingsFrom(context: Context, settingsClass: Class<T>): T`.

Using the above `interface` we can do the following to initialize an instance of our `MainPreferences` 
interface:

```kotlin
val settings: MainPreferences = context.getEasyPreferences(MainPreferences::class.java)
```

Now, getting and setting the fields of the generated `MainPreferences` object will access
SharedPreferences behind the scenes.

`settings.string = "New Value"` is equivalent to `sharedPreferences.edit().putString("string", "New Value").apply()`

and

`val savedValue = settings.string` is equivalent to `val savedValue = sharedPreferences.getString("string", "Default String")`
