package com.nolawiworkineh.agenda.presentation.agenda_overview

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class AgendaOverviewViewModel : ViewModel() {

    private val _state = MutableStateFlow(AgendaOverviewState())
    val state = _state.asStateFlow()


    fun onAction(action: AgendaOverviewActions) {
        when (action) {
            is AgendaOverviewActions.OnDatePickerClicked -> {
                _state.update { it.copy(isDatePickerVisible = true) }
            }

            is AgendaOverviewActions.OnDateSelected -> {


                _state.update {
                    it.copy(
                        selectedDate = action.date,
                        isDatePickerVisible = false
                    )
                }
            }

            is AgendaOverviewActions.OnDatePickerDismissed -> {
                _state.update { it.copy(isDatePickerVisible = false) }
            }

            else -> {}
        }
    }
}
