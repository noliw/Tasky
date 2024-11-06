package com.nolawiworkineh.agenda.presentation

import kotlinx.serialization.Serializable

sealed interface AgendaScreenRoutes {
    @Serializable
    data object HomeScreen : AgendaScreenRoutes
}