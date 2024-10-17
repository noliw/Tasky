package com.nolawiworkineh.auth.data

import com.nolawiworkineh.auth.domain.PatternValidator

object NamePatternValidator : PatternValidator {
    private const val MIN = 4
    private const val MAX = 50
    override fun matches(value: String): Boolean {
        val trimmed = value.trim()
        val words = trimmed.split("")

        val hasValidLength = trimmed.length in MIN..MAX
        val hasTwoWords = words.size >= 2
        val hasOnlyLetters = words.all { word ->
            word.all { char ->
                char.isLetter()
            }
        }

        return hasTwoWords && hasOnlyLetters && hasValidLength
    }
}
