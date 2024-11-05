package com.nolawiworkineh.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nolawiworkineh.designsystem.Theme.TaskyBlack
import com.nolawiworkineh.designsystem.Theme.TaskyGray

@Composable
fun TaskyScaffold(
    modifier: Modifier = Modifier,
    isStartFab: Boolean = false,
    isBlackToolBar: Boolean = true,
    topAppBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            topAppBar()
            if (!isBlackToolBar) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    thickness = 2.dp,
                    color = TaskyGray
                )
            }
        },
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = if (isStartFab) FabPosition.Start else FabPosition.End,
        modifier = modifier,
        containerColor = TaskyBlack
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding())
        ) {
            TaskyBackground {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = padding.calculateBottomPadding())
                ) {
                    content()
                }
            }
        }
    }
}

