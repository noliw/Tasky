@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.nolawiworkineh.auth.presentation.register

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nolawiworkineh.auth.presentation.R
import com.nolawiworkineh.core.presentation.ui.ObserveAsEvents
import com.nolawiworkineh.designsystem.Theme.ArrowLeftIcon
import com.nolawiworkineh.designsystem.Theme.CheckMarkIcon
import com.nolawiworkineh.designsystem.Theme.TaskyTheme
import com.nolawiworkineh.designsystem.Theme.TaskyWhite
import com.nolawiworkineh.designsystem.components.TaskyButton
import com.nolawiworkineh.designsystem.components.TaskyFloatingActionButton
import com.nolawiworkineh.designsystem.components.TaskyPasswordTextField
import com.nolawiworkineh.designsystem.components.TaskyScaffold
import com.nolawiworkineh.designsystem.components.TaskyTextField
import com.nolawiworkineh.designsystem.components.TaskyTopAppBar


@Composable
fun RegisterScreenRoot(
    navigateBackToLoginClick: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is RegisterEvent.RegistrationSuccess -> {
                Toast.makeText(
                    context,
                    context.getString(R.string.registration_successful),
                    Toast.LENGTH_LONG
                ).show()
                navigateBackToLoginClick()
            }

            is RegisterEvent.RegistrationFailure -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }
    RegisterScreen(
        state = viewModel.state.collectAsStateWithLifecycle().value,
        onAction = {
            action ->
                when(action) {
                    is RegisterAction.OnNavigateBackToLoginClick -> navigateBackToLoginClick()
                    else -> Unit
                }
                viewModel.onAction(action)


        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable

private fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    TaskyScaffold(
        modifier = Modifier,
        isStartFab = true,
        isBlackToolBar = true,
        topAppBar = {
            TaskyTopAppBar(
                isBlackToolBar = true,
                isWhiteText = true,
                customTitle = {
                    Text(
                        text = "Create your account",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Medium,
                        color = TaskyWhite
                    )
                },
            )
        },
        floatingActionButton = {
            TaskyFloatingActionButton(
                icon = ArrowLeftIcon,
                onClick = { onAction(RegisterAction.OnNavigateBackToLoginClick) },
                contentDescription = "Back to login",
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TaskyTextField(
                modifier = Modifier.padding(top = 16.dp),
                state = state.fullName,
                hint = "Full Name",
                isError = !state.isFullNameValid,
                imeAction = ImeAction.Next,
                endIcon = null

            )

            Spacer(modifier = Modifier.height(16.dp))

            TaskyTextField(
                state = state.email,
                hint = "Email address",
                endIcon = if (state.isEmailValid) CheckMarkIcon else null,
                imeAction = ImeAction.Next,
                isError = !state.isEmailValid,
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(16.dp))

            TaskyPasswordTextField(
                state = state.password,
                hint = "Password",
                modifier = Modifier
                    .fillMaxWidth(),
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = { onAction(RegisterAction.OnTogglePasswordVisibilityClick) }
            )

            Spacer(modifier = Modifier.height(48.dp))

            TaskyButton(text = "GET STARTED",
                isLoading = state.isRegistering,
                enabled = state.canRegister,
                modifier = Modifier.fillMaxWidth(),
                onClick = { onAction(RegisterAction.OnRegisterClick) }

            )
            // Add other content as needed
        }

    }

}

@Preview
@Composable
private fun RegisterScreenPreview() {
    TaskyTheme {
        RegisterScreen(
            state = RegisterState(),
            onAction = {}
        )
    }
}