package com.nolawiworkineh.agenda.presentation.agenda_overview

import java.time.LocalDate

sealed interface AgendaOverviewActions {

    data object OnAddNewAgendaItem: AgendaOverviewActions

    // Profile actions
    data object OnProfileClick : AgendaOverviewActions
    data object OnLogoutClick : AgendaOverviewActions

    // DatePicker actions
    data object OnDatePickerClicked: AgendaOverviewActions
    data class OnDateSelected(val date: LocalDate): AgendaOverviewActions
    data object OnDatePickerDismissed: AgendaOverviewActions

}