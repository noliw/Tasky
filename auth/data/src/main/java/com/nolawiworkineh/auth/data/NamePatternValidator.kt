package com.nolawiworkineh.auth.data

import com.nolawiworkineh.auth.domain.PatternValidator

object NamePatternValidator : PatternValidator {
    override fun matches(value: String): Boolean {
        val name = value.trim()

        if (name.length !in 4..50) {
            return false
        }
        // The regex pattern checks for at least two words with letters only, separated by space
        val namePattern = "^[A-Za-z]+(?:\\s[A-Za-z]+)+$".toRegex()
        return namePattern.matches(value.trim())
    }
}
