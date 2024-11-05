package com.nolawiworkineh.auth.presentation.login


import androidx.compose.foundation.text.input.TextFieldState
import com.nolawiworkineh.auth.domain.PasswordValidationState



data class LoginState(
    // email
    val email: TextFieldState = TextFieldState(),
    val isEmailValid: Boolean = false,

    // password
    val password: TextFieldState = TextFieldState(),
    val isPasswordVisible: Boolean = false,
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),

    // login
    val isLoggingIn: Boolean = false,
    val enableLoginButton: Boolean = false
)

