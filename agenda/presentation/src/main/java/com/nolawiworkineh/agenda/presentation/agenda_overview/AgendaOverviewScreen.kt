package com.nolawiworkineh.agenda.presentation.agenda_overview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nolawiworkineh.designsystem.Theme.LogoutIcon
import com.nolawiworkineh.designsystem.Theme.PlusIcon
import com.nolawiworkineh.designsystem.Theme.TaskyRed
import com.nolawiworkineh.designsystem.Theme.TaskyTheme
import com.nolawiworkineh.designsystem.components.CalendarDaysRow
import com.nolawiworkineh.designsystem.components.TaskyDatePicker
import com.nolawiworkineh.designsystem.components.TaskyFloatingActionButton
import com.nolawiworkineh.designsystem.components.TaskyScaffold
import com.nolawiworkineh.designsystem.components.TaskyTopAppBar
import com.nolawiworkineh.designsystem.components.formatMonth
import com.nolawiworkineh.designsystem.components.util.DropDownMenuItem

@Composable
fun AgendaScreenRoot(
    viewModel: AgendaOverviewViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    AgendaScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AgendaScreen(
    state: AgendaOverviewState,
    onAction: (AgendaOverviewActions) -> Unit
) {
    TaskyScaffold(
        modifier = Modifier,
        isStartFab = false,
        isBlackToolBar = true,
        topAppBar = {
            TaskyTopAppBar(
                modifier = Modifier,
                isBlackToolBar = true,
                showEndIcon = true,
                isWhiteText = true,
                customTitle = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onAction(AgendaOverviewActions.OnDatePickerClicked)
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = state.selectedDate.formatMonth(),
                                style = MaterialTheme.typography.titleLarge
                            )
                            Icon(
                                imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = "Select date",
                            )
                        }
                    }

                },
                actionIcons = {},
                endIcon = {},
                menuItems = listOf(
                    DropDownMenuItem(
                        title = "Logout",
                        icon = LogoutIcon,
                        tint = TaskyRed
                    )
                ),
                onMenuItemClick = { index ->
                    when (index) {
                        0 -> onAction(AgendaOverviewActions.OnLogoutClick)
                    }
                },
            )
        },
        floatingActionButton = {
            TaskyFloatingActionButton(
                icon = PlusIcon,
                onClick = { onAction(AgendaOverviewActions.OnAddNewAgendaItem) },
                contentDescription = "Add new agenda item",
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            CalendarDaysRow(
                selectedDate = state.selectedDate,
                onDateSelected = { date ->
                    onAction(AgendaOverviewActions.OnDateSelected(date))
                },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            )
        }

        if (state.isDatePickerVisible) {
            TaskyDatePicker(
                selectedDate = state.selectedDate,
                isVisible = true,
                onDateSelected = { date ->
                    onAction(AgendaOverviewActions.OnDateSelected(date))
                },
                onDismiss = {
                    onAction(AgendaOverviewActions.OnDatePickerDismissed)
                }
            )
        }
    }
}

@Preview
@Composable
private fun AgendaScreenPreview() {
    TaskyTheme {
        AgendaScreen(
            state = AgendaOverviewState(),
            onAction = {}
        )
    }
}