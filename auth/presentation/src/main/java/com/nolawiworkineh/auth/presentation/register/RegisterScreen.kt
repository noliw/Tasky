@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.nolawiworkineh.auth.presentation.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
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
    OnNavigateBackToLoginClick: () -> Unit,
    viewModel: RegisterViewModel
) {
    RegisterScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

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
                onClick = {},
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
                state = rememberTextFieldState(),
                hint = "Full Name",
                endIcon = null

            )

            Spacer(modifier = Modifier.height(16.dp))

            TaskyTextField(
                state = rememberTextFieldState(),
                hint = "Email address",
                endIcon = CheckMarkIcon,
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(16.dp))

            TaskyPasswordTextField(
                state = rememberTextFieldState(),
                hint = "Password",
                modifier = Modifier
                    .fillMaxWidth(),
                isPasswordVisible = false,
                onTogglePasswordVisibility = {}
            )

            Spacer(modifier = Modifier.height(48.dp))

            TaskyButton(text = "GET STARTED",
                isLoading = false,
                enabled = true,
                modifier = Modifier.fillMaxWidth(),
                onClick = {}

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