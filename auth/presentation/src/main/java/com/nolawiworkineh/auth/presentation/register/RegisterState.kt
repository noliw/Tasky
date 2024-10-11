package com.nolawiworkineh.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text.input.TextFieldState
import com.nolawiworkineh.auth.domain.PasswordValidationState



@OptIn(ExperimentalFoundationApi::class)
data class RegisterState(
    // full name
    val fullName: TextFieldState = TextFieldState(),
    val isFullNameValid: Boolean = false,

    // email
    val email: TextFieldState = TextFieldState(),
    val isEmailValid: Boolean = false,

    // password
    val password: TextFieldState = TextFieldState(),
    val isPasswordVisible: Boolean = false,
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),

    // register
    val isRegistering: Boolean = false,
    val canRegister: Boolean = false
)

