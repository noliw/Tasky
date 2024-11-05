package com.nolawiworkineh.agenda.presentation.agenda_overview

import java.time.LocalDate

data class AgendaOverviewState(
    val selectedDate: LocalDate = LocalDate.now(),
    val isDatePickerVisible: Boolean = false
)