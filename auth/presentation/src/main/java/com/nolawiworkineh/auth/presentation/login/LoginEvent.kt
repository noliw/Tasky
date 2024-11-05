package com.nolawiworkineh.auth.presentation.login

import com.nolawiworkineh.core.presentation.ui.UiText

sealed interface LoginEvent {

    data object LoginSuccess : LoginEvent

    data class LoginFailure(val error: UiText) : LoginEvent
}