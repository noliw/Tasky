package com.nolawiworkineh.designsystem.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nolawiworkineh.designsystem.Theme.TaskyBlack
import com.nolawiworkineh.designsystem.Theme.TaskyGreen
import com.nolawiworkineh.designsystem.Theme.TaskyLightGray
import com.nolawiworkineh.designsystem.Theme.TaskyTextGray
import com.nolawiworkineh.designsystem.Theme.TaskyTheme
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
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = TaskyBlack,
            contentColor = TaskyWhite,
            disabledContainerColor = TaskyLightGray,
            disabledContentColor = TaskyTextGray
        ),
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 24.dp),
        modifier = modifier
            .height(IntrinsicSize.Min)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp)
                    .semantics { contentDescription = "Loading" },
                strokeWidth = 2.dp,
                color = TaskyGreen
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                color = if (enabled) TaskyWhite else TaskyTextGray
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun TaskyButtonPreview() {
    TaskyTheme {
        TaskyButton(
            text = "LOG IN",
            isLoading = false,
            enabled = false,
            onClick = {}
        )
    }
}
