package com.nolawiworkineh.agenda.presentation.agenda_overview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.nolawiworkineh.designsystem.Theme.TaskyTheme
import com.nolawiworkineh.designsystem.components.TaskyScaffold

@Composable
fun AgendaScreenRoot(
    viewModel: AgendaOverviewViewModel = hiltViewModel()
) {
    AgendaScreen(
//        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun AgendaScreen(
//    state: AgendaOverviewState,
    onAction: (AgendaOverviewActions) -> Unit
) {
    TaskyScaffold(

    ){

    }


}

@Preview
@Composable
private fun AgendaScreenPreview() {
    TaskyTheme {
        AgendaScreen(
//            state = AgendaOverviewState(),
            onAction = {}
        )
    }
}