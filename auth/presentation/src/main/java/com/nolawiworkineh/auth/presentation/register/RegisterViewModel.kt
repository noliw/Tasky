package com.nolawiworkineh.auth.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nolawiworkineh.auth.domain.UserDataValidator
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RegisterViewModel(
    private val userDataValidator: UserDataValidator,
) : ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    init {
        snapshotFlow {  state.email }
            .onEach { currentName ->
                val isValidName = userDataValidator.isValidName(currentName.toString())
                state = state.copy(
                    isFullNameValid = isValidName,
                    canRegister = isValidName && state.isEmailValid && state.passwordValidationState.isValidPassword && !state.isRegistering
                )
            }
            .launchIn(viewModelScope)



        snapshotFlow {  state.email }
            .onEach { currentEmail ->
                val isEmailValid = userDataValidator.isValidEmail(currentEmail.toString())
                state = state.copy(
                    isEmailValid = isEmailValid,
                    canRegister = isEmailValid && state.isFullNameValid && state.passwordValidationState.isValidPassword && !state.isRegistering
                )
            }
            .launchIn(viewModelScope)


        snapshotFlow { state.password }
            .onEach { currentPassword ->
                val passwordValidationState = userDataValidator.validatePassword(currentPassword.toString())
                state = state.copy(
                    passwordValidationState = passwordValidationState,
                    canRegister = state.isEmailValid && state.isFullNameValid && passwordValidationState.isValidPassword && !state.isRegistering
                )
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: RegisterAction){

    }
}