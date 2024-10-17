package com.nolawiworkineh.auth.domain

class UserDataValidator(private val patternValidator: PatternValidator) {

    fun isValidName(name: String): Boolean {
        return patternValidator.matches(name.trim())
    }

    fun isValidEmail(email: String): Boolean {
        return patternValidator.matches(email.trim())
    }

    fun validatePassword(password: String): PasswordValidationState {
        val trimmedPassword = password.trim()
        val hasMinLength = trimmedPassword.length >= MIN_PASSWORD_LENGTH
        val hasUppercase = trimmedPassword.any { it.isUpperCase() }
        val hasLowercase = trimmedPassword.any { it.isLowerCase() }
        val hasDigit = trimmedPassword.any { it.isDigit() }

        return PasswordValidationState(
            hasMinLength = hasMinLength,
            hasUppercase = hasUppercase,
            hasLowercase = hasLowercase,
            hasNumber = hasDigit,
        )
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 9
    }
}
