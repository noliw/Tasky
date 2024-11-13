@file:OptIn(ExperimentalMaterial3Api::class)

package com.nolawiworkineh.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nolawiworkineh.designsystem.Theme.TaskyTheme
import com.nolawiworkineh.designsystem.Theme.TaskyWhite
import com.nolawiworkineh.designsystem.components.util.DropDownMenuItem


@Composable
fun TaskyTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    showBackButton: Boolean = false,
    isBlackToolBar: Boolean = true,
    showEndIcon: Boolean = false,
    isWhiteText: Boolean = false,
    blackHeightFraction: Float = 0.16f,
    customTitle: (@Composable () -> Unit)? = null,
    navigationIcon: (@Composable () -> Unit)? = null,
    actionIcons: (@Composable RowScope.() -> Unit)? = null,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    endIcon: (@Composable (onClick: () -> Unit) -> Unit)? = null,
    menuItems: List<DropDownMenuItem> = emptyList(),
    onMenuItemClick: (Int) -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    var isDropDownOpen by rememberSaveable { mutableStateOf(false) }

    // Get screen height in dp and multiply by fraction
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val toolbarHeight = screenHeight * blackHeightFraction
//    val blackToolBarHeight = toolbarHeight.value.dp
    TopAppBar(

        title = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = if (customTitle != null) 18.dp else 0.dp),
                contentAlignment = Alignment.Center
            ) {
                if (customTitle != null) {
                    customTitle()
                } else {
                    Text(
                        text = title,
                        color = if (isWhiteText) Color.White else Color.Black,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        },

        modifier = modifier.height(toolbarHeight),


        scrollBehavior = scrollBehavior,

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = if (isBlackToolBar) Color.Black else Color.White,
        ),

        navigationIcon = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentSize(align = Alignment.Center)
            ) {
                if (showBackButton) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = if (isWhiteText) Color.White else Color.Black
                        )
                    }
                } else {
                    navigationIcon?.invoke()
                }
            }
        },

        actions = {
            Row(
                modifier = Modifier
                    .fillMaxHeight(), // Ensure actions take the full height
                verticalAlignment = Alignment.CenterVertically
            ) {
                // If actionIcons are provided, invoke them (e.g., Save, Edit)
                actionIcons?.invoke(this)

                // Handle the dynamic end icon and menu items
                if (endIcon != null) {
                    endIcon { isDropDownOpen = !isDropDownOpen }

                    // Dropdown menu items - render after the IconButton
                    DropdownMenu(
                        expanded = isDropDownOpen,
                        onDismissRequest = { isDropDownOpen = false }
                    ) {
                        menuItems.forEachIndexed { index, item ->
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp)
                                    ) {
                                        item.icon?.let {
                                            Icon(
                                                imageVector = it,
                                                contentDescription = item.title,
                                                tint = item.tint
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(text = item.title)
                                    }
                                },
                                onClick = {
                                    onMenuItemClick(index)
                                    isDropDownOpen = false
                                }
                            )
                        }
                    }
                }
            }
        }



    )
}

@Preview(showBackground = true)
@Composable
fun TaskyTopAppBarWithBackButtonPreview() {
    TaskyTopAppBar(
        title = "Task Details",
        showBackButton = true,
        isBlackToolBar = true,
        isWhiteText = true,
        navigationIcon = {
            IconButton(onClick = { /* Handle back navigation */ }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        actionIcons = {

            IconButton(onClick = { /* Handle save action */ }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Save",
                    tint = Color.White
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TaskyTopAppBarWithDropdownMenuPreview() {
    TaskyTopAppBar(
        title = "Task List",
        showEndIcon = true,
        isBlackToolBar = false,
        isWhiteText = false,
        menuItems = listOf(
            DropDownMenuItem(
                icon = Icons.Default.Edit,
                title = "Edit"
            ),
            DropDownMenuItem(
                icon = Icons.Default.Delete,
                title = "Delete"
            )
        ),
        onMenuItemClick = { index ->
            // Handle menu item click here
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TaskyTopAppBarWithCustomEndIconPreview() {
    TaskyTheme {
        TaskyTopAppBar(
            title = "Task Overview",
            showEndIcon = false,
            isBlackToolBar = true,
            isWhiteText = true,
            navigationIcon = null,
            customTitle = {

                Text(
                    text = "Create your account",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Medium,
                    color = TaskyWhite
                )
            }

        )
    }
}
