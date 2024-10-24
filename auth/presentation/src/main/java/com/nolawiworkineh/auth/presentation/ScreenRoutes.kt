@file:Suppress("PLUGIN_IS_NOT_ENABLED")

package com.nolawiworkineh.auth.presentation

import kotlinx.serialization.Serializable

sealed interface AuthScreenRoutes {
    @Serializable
    data object RegisterScreen : AuthScreenRoutes
    @Serializable
    data object LoginScreen : AuthScreenRoutes
}