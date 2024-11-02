package com.nolawiworkineh.auth.presentation.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Color
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
import com.nolawiworkineh.designsystem.Theme.CheckMarkIcon
import com.nolawiworkineh.designsystem.Theme.TaskyTextGray
import com.nolawiworkineh.designsystem.Theme.TaskyTheme
import com.nolawiworkineh.designsystem.Theme.TaskyWhite
import com.nolawiworkineh.designsystem.components.TaskyButton
import com.nolawiworkineh.designsystem.components.TaskyPasswordTextField
import com.nolawiworkineh.designsystem.components.TaskyScaffold
import com.nolawiworkineh.designsystem.components.TaskyTextField
import com.nolawiworkineh.designsystem.components.TaskyTopAppBar

@Composable
fun LoginScreenRoot(
    navigateToRegisterScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveAsEvents(flow = viewModel.events) { event ->
        when (event) {
            is LoginEvent.LoginFailure -> {
                keyboardController?.hide()
                Toast.makeText(context, event.error.asString(context), Toast.LENGTH_LONG).show()
            }

            LoginEvent.LoginSuccess -> {
                keyboardController?.hide()
                Toast.makeText(context, R.string.your_logged_in, Toast.LENGTH_LONG).show()
                navigateToHomeScreen()
            }
        }
    }

    LoginScreen(
        state = viewModel.state.collectAsStateWithLifecycle().value,
        onAction = {
                action ->
            when(action) {
                is LoginAction.OnNavigateToRegisterClick -> navigateToRegisterScreen()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable

private fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit
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
                        text = "Welcome Back!",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Medium,
                        color = TaskyWhite
                    )
                },
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

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
                isError = !state.passwordValidationState.isValidPassword && state.password.text.isNotEmpty(),
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = { onAction(LoginAction.OnTogglePasswordVisibilityClick) }
            )

            Spacer(modifier = Modifier.height(48.dp))

            TaskyButton(text = "LOG IN",
                isLoading = state.isLoggingIn,
                enabled = state.enableLoginButton,
                modifier = Modifier.fillMaxWidth(),
                onClick = { onAction(LoginAction.OnLoginClick) }

            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "DON'T HAVE AN ACCOUNT? ",
                    color = TaskyTextGray.copy(alpha = 0.6f),
                )
                Text(
                    text = "SIGN UP",
                    color = Color.Blue.copy(alpha = 0.6f),
                    modifier = Modifier.clickable { onAction(LoginAction.OnNavigateToRegisterClick) }
                )
            }
        }

    }

}

@Preview
@Composable
private fun LoginScreenPreview() {
    TaskyTheme {
        LoginScreen(
            state = LoginState(),
            onAction = {}
        )
    }
}