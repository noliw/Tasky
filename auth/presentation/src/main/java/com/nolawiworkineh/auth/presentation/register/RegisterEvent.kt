package com.nolawiworkineh.auth.presentation.register

import com.nolawiworkineh.core.presentation.ui.UiText

sealed interface RegisterEvent {

    data object RegistrationSuccess : RegisterEvent

    data class RegistrationFailure(val error: UiText) : RegisterEvent
}