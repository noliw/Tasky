package com.nolawiworkineh.agenda.presentation.agenda_overview

sealed interface AgendaOverviewActions {

    data object OnAddNewAgendaItem: AgendaOverviewActions

    // Profile actions
    data object OnProfileClick : AgendaOverviewActions
    data object OnLogoutClick : AgendaOverviewActions

}