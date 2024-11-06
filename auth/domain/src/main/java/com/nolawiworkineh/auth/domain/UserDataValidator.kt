package com.nolawiworkineh.auth.domain

class UserDataValidator(private val emailPatternValidator: PatternValidator,
                        private val namePatternValidator: PatternValidator) {

    fun isValidName(name: String): Boolean {
        return namePatternValidator.matches(name.trim())
    }

    fun isValidEmail(email: String): Boolean {
        return emailPatternValidator.matches(email.trim())
    }

    fun validatePassword(password: String): PasswordValidationState {
        val hasMinLength = password.length >= MIN_PASSWORD_LENGTH
        val hasUppercase = password.any { it.isUpperCase() }
        val hasLowercase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }

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
