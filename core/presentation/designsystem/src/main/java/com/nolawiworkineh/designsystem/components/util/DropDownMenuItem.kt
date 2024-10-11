package com.nolawiworkineh.designsystem.components.util

import androidx.compose.ui.graphics.vector.ImageVector

data class DropDownMenuItem(
    // **icon**: An ImageVector representing the icon for the dropdown menu item (e.g., a settings or logout icon).
    val icon: ImageVector? = null,

    // **title**: A string representing the text that describes the action or option in the dropdown (e.g., "Logout", "Settings").
    val title: String
)