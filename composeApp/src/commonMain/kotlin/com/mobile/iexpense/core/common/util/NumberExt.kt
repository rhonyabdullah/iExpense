package com.mobile.iexpense.core.common.util

import kotlin.math.pow
import kotlin.math.roundToLong

/**
 * Formats a double to a string with a fixed number of decimal places.
 * This is a pure Kotlin alternative to String.format which is not multiplatform-safe.
 */
fun Double.toCurrencyString(digits: Int = 2): String {
    val multiplier = 10.0.pow(digits)
    val rounded = (this * multiplier).roundToLong() / multiplier
    val s = rounded.toString()
    
    return if (s.contains(".")) {
        val parts = s.split(".")
        val integerPart = parts[0]
        var decimalPart = parts[1]
        if (decimalPart.length < digits) {
            decimalPart = decimalPart.padEnd(digits, '0')
        } else if (decimalPart.length > digits) {
            decimalPart = decimalPart.substring(0, digits)
        }
        "$integerPart.$decimalPart"
    } else {
        "$s." + "0".repeat(digits)
    }
}
