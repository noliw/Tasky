package com.nolawiworkineh.designsystem.components.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.nolawiworkineh.designsystem.Theme.TaskyWhite

data class DropDownMenuItem(
    val icon: ImageVector? ,
    val tint: Color = TaskyWhite,
    val title: String
)