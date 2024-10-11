package com.nolawiworkineh.auth.presentation.register

sealed interface RegisterAction {

    data object OnTogglePasswordVisibilityClick : RegisterAction

    data object OnNavigateBackToLoginClick : RegisterAction

    data object OnRegisterClick : RegisterAction
}