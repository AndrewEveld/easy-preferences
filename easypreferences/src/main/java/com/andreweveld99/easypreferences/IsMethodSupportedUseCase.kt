package com.andreweveld99.easypreferences

import java.lang.reflect.Method

class IsMethodSupportedUseCase {
    fun execute(method: Method): Boolean {
        if (method.isDefault) return true
        if (method.name.length < 4) return false
        val methodPrefix = method.name.substring(0, 3)
        if (methodPrefix == "get" || methodPrefix == "set") return true
        return false
    }
}