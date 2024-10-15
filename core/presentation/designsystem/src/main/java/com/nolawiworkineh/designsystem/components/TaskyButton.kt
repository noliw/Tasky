package com.nolawiworkineh.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nolawiworkineh.designsystem.Theme.TaskyBlack
import com.nolawiworkineh.designsystem.Theme.TaskyGreen
import com.nolawiworkineh.designsystem.Theme.TaskyLightGray
import com.nolawiworkineh.designsystem.Theme.TaskyTextGray
import com.nolawiworkineh.designsystem.Theme.TaskyWhite

@Composable
fun TaskyButton(
    text: String,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = TaskyBlack,
            contentColor = TaskyWhite,
            disabledContainerColor = TaskyLightGray,
            disabledContentColor = TaskyTextGray
        ),
        shape = RoundedCornerShape(90f),
        modifier = modifier
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .alpha(if (isLoading) 1f else 0f),
                strokeWidth = 1.5.dp,
                color = TaskyGreen
            )
            Text(
                text = text,
                modifier = Modifier
                    .alpha(if (isLoading) 0f else 1f),
                style = MaterialTheme.typography.displaySmall,
                color = if (enabled) TaskyWhite else TaskyTextGray
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun TaskyButtonPreview() {
    TaskyButton(
        text = "LOG IN",
        isLoading = false,
        enabled = true,
        onClick = {}
    )
}
