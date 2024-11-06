package com.nolawiworkineh.auth.data

import com.nolawiworkineh.auth.domain.PatternValidator

object NamePatternValidator : PatternValidator {
    private const val MIN = 4
    private const val MAX = 50
    override fun matches(value: String): Boolean {
        val trimmed = value.trim()

        val hasValidLength = trimmed.length in MIN..MAX

        val hasValidCharacters = trimmed.all { char ->
            char.isLetter() || char.isWhitespace()
        }


        return hasValidCharacters && hasValidLength
    }
}
