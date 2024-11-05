package com.nolawiworkineh.agenda.presentation.agenda

sealed interface AgendaActions {

    data object OnAddNewAgendaItem: AgendaActions

    // Profile actions
    data object OnProfileClick : AgendaActions
    data object OnLogoutClick : AgendaActions

}