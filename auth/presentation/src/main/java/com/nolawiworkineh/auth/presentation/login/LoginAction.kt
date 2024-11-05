package com.nolawiworkineh.auth.presentation.login

sealed interface LoginAction {

    data object OnTogglePasswordVisibilityClick : LoginAction

    data object OnNavigateToRegisterClick : LoginAction

    data object OnLoginClick : LoginAction
}