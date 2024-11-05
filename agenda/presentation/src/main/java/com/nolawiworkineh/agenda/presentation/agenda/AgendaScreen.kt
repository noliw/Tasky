package com.nolawiworkineh.agenda.presentation.agenda

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.nolawiworkineh.designsystem.Theme.TaskyTheme

@Composable
fun AgendaScreenRoot(
    viewModel: AgendaViewModel = hiltViewModel()
) {
    AgendaScreen(
//        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun AgendaScreen(
//    state: AgendaState,
    onAction: (AgendaActions) -> Unit
) {


}

@Preview
@Composable
private fun AgendaScreenPreview() {
    TaskyTheme {
        AgendaScreen(
//            state = AgendaState(),
            onAction = {}
        )
    }
}