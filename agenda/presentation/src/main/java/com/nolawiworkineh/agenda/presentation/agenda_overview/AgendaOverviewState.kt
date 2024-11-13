package com.nolawiworkineh.agenda.presentation.agenda_overview

import com.nolawiworkineh.core.domain.util.InitialGenerator
import java.time.LocalDate

data class AgendaOverviewState(
    // Profile
    val fullName: String = "",
    val initials: String = InitialGenerator.generateInitial(fullName),
    val isProfileMenuVisible: Boolean = false,

    // Date
    val selectedDate: LocalDate = LocalDate.now(),
    val isDatePickerVisible: Boolean = false
)