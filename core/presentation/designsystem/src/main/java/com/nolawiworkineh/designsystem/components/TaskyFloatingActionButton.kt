package com.nolawiworkineh.designsystem.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nolawiworkineh.designsystem.Theme.ArrowLeftIcon
import com.nolawiworkineh.designsystem.Theme.TaskyBlack
import com.nolawiworkineh.designsystem.Theme.TaskyWhite

@Composable
fun TaskyFloatingActionButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    iconSize: Dp = 36.dp
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier.padding(12.dp),
        containerColor = TaskyBlack,
        contentColor = TaskyWhite,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = TaskyWhite,
            modifier = Modifier.size(iconSize)
        )

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TaskyFloatingActionButtonPreview() {
    TaskyFloatingActionButton(
        icon = ArrowLeftIcon,
        onClick = {},
    )
}
