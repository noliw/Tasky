package com.nolawiworkineh.auth.presentation.register

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
class RegisterViewModel @Inject constructor(
    private val userDataValidator: UserDataValidator,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(RegisterState())
    var state: StateFlow<RegisterState> = _state.onStart {
        observeNameChanges()
        observeEmailChanges()
        observePasswordChanges()
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), RegisterState())

    private fun observeNameChanges() {
        snapshotFlow { _state.value.fullName.text }
            .onEach { currentName ->
                val isValidName = userDataValidator.isValidName(currentName.toString())
                _state.update {
                    it.copy(
                        isFullNameValid = isValidName,
                        enableRegisterButton = isValidName && _state.value.isEmailValid && _state.value.passwordValidationState.isValidPassword && !_state.value.isRegistering
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observeEmailChanges() {
        snapshotFlow { _state.value.email.text }
            .onEach { currentEmail ->
                val isEmailValid = userDataValidator.isValidEmail(currentEmail.toString())
                _state.update {
                    it.copy(
                        isEmailValid = isEmailValid,
                        enableRegisterButton = isEmailValid && _state.value.isFullNameValid && _state.value.passwordValidationState.isValidPassword && !_state.value.isRegistering
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
                _state.update {
                    it.copy(
                        passwordValidationState = passwordValidationState,
                        enableRegisterButton = _state.value.isEmailValid && _state.value.isFullNameValid && passwordValidationState.isValidPassword && !_state.value.isRegistering
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnRegisterClick -> {
                register()
            }

            RegisterAction.OnTogglePasswordVisibilityClick -> {
                _state.value = _state.value.copy(
                    isPasswordVisible = !_state.value.isPasswordVisible
                )

            }
            else -> {}
        }
    }

    private fun register() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isRegistering = true)
            val result = authRepository.register(
                fullName = _state.value.fullName.text.trim().toString(),
                email = _state.value.email.text.toString().trim(),
                password = _state.value.password.text.toString()
            )
            _state.value = _state.value.copy(isRegistering = false)
            when (result) {
                is Result.Error -> {
                    if (result.error == DataError.Network.CONFLICT) {
                        eventChannel.send(RegisterEvent.RegistrationFailure(UiText.StringResource(R.string.error_user_already_exists)))
                        return@launch
                    } else {
                        eventChannel.send(RegisterEvent.RegistrationFailure(result.error.toUiText()))
                    }
                }

                is Result.Success -> {
                    eventChannel.send(RegisterEvent.RegistrationSuccess)
                }
            }
        }
    }
}