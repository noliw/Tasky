package com.nolawiworkineh.auth.presentation.login

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nolawiworkineh.auth.domain.AuthRepository
import com.nolawiworkineh.auth.domain.UserDataValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userDataValidator: UserDataValidator,
    private val authRepository: AuthRepository
) : ViewModel()  {

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()


    private val _state = MutableStateFlow(LoginState())
    var state: StateFlow<LoginState> = _state.onStart {
        observeEmailChanges()
        observePasswordChanges()
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LoginState())


    private fun observeEmailChanges() {
        snapshotFlow { _state.value.email.text }
            .onEach { currentEmail ->
                val isEmailValid = userDataValidator.isValidEmail(currentEmail.toString())
                _state.value = _state.value.copy(
                    isEmailValid = isEmailValid,
                    enableLoginButton = isEmailValid && _state.value.passwordValidationState.isValidPassword && !_state.value.isLoggingIn
                )
            }
            .launchIn(viewModelScope)
    }

    private fun observePasswordChanges() {
        snapshotFlow { _state.value.password.text }
            .onEach { currentPassword ->
                val passwordValidationState =
                    userDataValidator.validatePassword(currentPassword.toString())
                _state.value = _state.value.copy(
                    passwordValidationState = passwordValidationState,
                    enableLoginButton = _state.value.isEmailValid && passwordValidationState.isValidPassword && !_state.value.isLoggingIn
                )
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: LoginAction){

    }
}