package com.nolawiworkineh.agenda.presentation.agenda_overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nolawiworkineh.core.domain.SessionStorage
import com.nolawiworkineh.core.domain.util.InitialGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgendaOverviewViewModel @Inject constructor(
    private val sessionStorage: SessionStorage
) : ViewModel() {

    private val _state = MutableStateFlow(AgendaOverviewState())
    val state: StateFlow<AgendaOverviewState> = _state.onStart {
        getInitials()
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AgendaOverviewState())

    private fun getInitials() {
        viewModelScope.launch {
            sessionStorage.get()?.let { authInfo ->
                _state.update { state ->
                    state.copy(
                        fullName = authInfo.fullName,
                        initials = InitialGenerator.generateInitial(authInfo.fullName)
                    )
                }
            }
        }
    }

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
            is AgendaOverviewActions.OnLogoutClick -> {
                viewModelScope.launch {
                    sessionStorage.set(null)
                }
            }
            is AgendaOverviewActions.OnProfileClick-> {
                _state.update { it.copy(isProfileMenuVisible = true) }
            }

            else -> {}
        }
    }
}
