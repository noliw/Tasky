package com.nolawiworkineh.auth.presentation.register

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nolawiworkineh.auth.domain.UserDataValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class RegisterViewModel(
    private val userDataValidator: UserDataValidator,
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    var state = _state.onStart {
        observeNameChanges()
        observeEmailChanges()
        observePasswordChanges()
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), RegisterState())

    private fun observeNameChanges() {
        snapshotFlow { _state.value.fullName }
            .onEach { currentName ->
                val isValidName = userDataValidator.isValidName(currentName.toString())
                _state.value = _state.value.copy(
                    isFullNameValid = isValidName,
                    canRegister = isValidName && _state.value.isEmailValid && _state.value.passwordValidationState.isValidPassword && !_state.value.isRegistering
                )
            }
            .launchIn(viewModelScope)
    }

    private fun observeEmailChanges() {
        snapshotFlow { _state.value.email }
            .onEach { currentEmail ->
                val isEmailValid = userDataValidator.isValidEmail(currentEmail.toString())
                _state.value = _state.value.copy(
                    isEmailValid = isEmailValid,
                    canRegister = isEmailValid && _state.value.isFullNameValid && _state.value.passwordValidationState.isValidPassword && !_state.value.isRegistering
                )
            }
            .launchIn(viewModelScope)
    }

    private fun observePasswordChanges(){
        snapshotFlow { _state.value.password }
            .onEach { currentPassword ->
                val passwordValidationState =
                    userDataValidator.validatePassword(currentPassword.toString())
                _state.value = _state.value.copy(
                    passwordValidationState = passwordValidationState,
                    canRegister = _state.value.isEmailValid && _state.value.isFullNameValid && passwordValidationState.isValidPassword && !_state.value.isRegistering
                )
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: RegisterAction) {

    }
}