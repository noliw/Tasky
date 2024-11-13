package com.nolawiworkineh.core.domain.util

object InitialGenerator {
    fun generateInitial(fullName: String): String {
        val nameParts = fullName.trim().split(" ").filter { it.isNotBlank() }
        return when {
            nameParts.size == 1 -> nameParts[0].take(2).uppercase()
            nameParts.size >= 2 -> nameParts[0].take(1).uppercase() + nameParts[nameParts.size - 1] .take(1).uppercase()
            else -> ""
        }
    }
}