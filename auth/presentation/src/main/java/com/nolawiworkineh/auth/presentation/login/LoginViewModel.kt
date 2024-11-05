package com.nolawiworkineh.auth.presentation.login

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nolawiworkineh.auth.domain.AuthRepository
import com.nolawiworkineh.auth.domain.UserDataValidator
import com.nolawiworkineh.auth.presentation.R
import com.nolawiworkineh.core.domain.util.DataError
import com.nolawiworkineh.core.domain.util.Result
import com.nolawiworkineh.core.presentation.ui.UiText
import com.nolawiworkineh.core.presentation.ui.toUiText
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userDataValidator: UserDataValidator,
    private val authRepository: AuthRepository
) : ViewModel() {

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
                _state.update {
                    it.copy(
                        isEmailValid = isEmailValid,
                        enableLoginButton = isEmailValid && _state.value.passwordValidationState.isValidPassword && !_state.value.isLoggingIn
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observePasswordChanges() {
        snapshotFlow { _state.value.password.text }
            .onEach { currentPassword ->
                val passwordValidationState =
                    userDataValidator.validatePassword(currentPassword.toString())
                _state.update  {
                   it.copy(
                        passwordValidationState = passwordValidationState,
                        enableLoginButton = _state.value.isEmailValid && passwordValidationState.isValidPassword && !_state.value.isLoggingIn
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnLoginClick -> login()
            is LoginAction.OnTogglePasswordVisibilityClick -> {
                _state.value = _state.value.copy(
                    isPasswordVisible = !_state.value.isPasswordVisible
                )
            }
            else -> {}
        }

    }

    private fun login() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoggingIn = true)
            val result = authRepository.login(
                email = _state.value.email.text.toString().trim(),
                password = _state.value.password.text.toString()
            )
            _state.value = _state.value.copy(isLoggingIn = true)

            when (result) {
                is Result.Error -> {
                    if (result.error == DataError.Network.UNAUTHORIZED) {
                        eventChannel.send(
                            LoginEvent.LoginFailure(
                                UiText.StringResource(
                                    R.string.email_or_password_is_incorrect
                                )
                            )
                        )
                        return@launch
                    } else {
                        eventChannel.send(LoginEvent.LoginFailure(result.error.toUiText()))
                    }
                }

                is Result.Success -> {
                    eventChannel.send(LoginEvent.LoginSuccess)
                }
            }
        }

    }
}