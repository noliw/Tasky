package com.nolawiworkineh.auth.presentation.register

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController


@Composable
fun RegisterScreenRoot(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
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

}

@Preview
@Composable
private fun RegisterScreenPreview() {
        RegisterScreen(
            state = RegisterState(),
            onAction = {}
        )
}