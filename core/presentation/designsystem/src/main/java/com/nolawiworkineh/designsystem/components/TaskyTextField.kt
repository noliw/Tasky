@file:OptIn(ExperimentalFoundationApi::class)
package com.nolawiworkineh.designsystem.components


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nolawiworkineh.designsystem.Theme.TaskyBlack
import com.nolawiworkineh.designsystem.Theme.TaskyGray
import com.nolawiworkineh.designsystem.Theme.TaskyGreen
import com.nolawiworkineh.designsystem.Theme.TaskyLightGray
import com.nolawiworkineh.designsystem.Theme.TaskyRed
import com.nolawiworkineh.designsystem.Theme.TaskyTextGray

@Composable
fun TaskyTextField(
    state: TextFieldState,
    endIcon: ImageVector?,
    hint: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false,
    imeAction: ImeAction = ImeAction.Done,
) {
    var isFocused by remember { mutableStateOf(false) }
    var hasBeenFocused by remember { mutableStateOf(false) }
    var showErrorIndicator by remember { mutableStateOf(false) }

    val shape = RoundedCornerShape(8.dp)

    val borderColor = when {
        showErrorIndicator -> TaskyRed
        isFocused -> TaskyTextGray
        else -> TaskyLightGray
    }

    BasicTextField(
        state = state,
        textStyle = LocalTextStyle.current.copy(
            color = TaskyBlack
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        lineLimits = TextFieldLineLimits.SingleLine,
        cursorBrush = SolidColor(TaskyTextGray),
        modifier = modifier
            .clip(shape)
            .background(TaskyLightGray)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = shape
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .height(48.dp)
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused

                if (isFocused) {
                    hasBeenFocused = true
                    showErrorIndicator = false // Reset error indicator when focused
                }

                if (!isFocused && hasBeenFocused) {
                    showErrorIndicator = isError
                }
            },
        decorator = { innerBox ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    if (state.text.isEmpty()) {
                        Text(
                            text = hint,
                            color = TaskyGray,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    innerBox()
                }
                if (endIcon != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = endIcon,
                        contentDescription = null,
                        tint = TaskyGreen,
                        modifier = Modifier
                            .padding(end = 8.dp)

                    )
                }
            }
        }
    )
}



@Preview(showBackground = true)
@Composable
private fun TaskyTextFieldPreview() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TaskyTextField(
            state = rememberTextFieldState(),
            endIcon = Icons.Default.Email,
            hint = "example@test.com",
            imeAction = ImeAction.Next,
            modifier = Modifier
                .fillMaxWidth()
        )
        TaskyTextField(
            state = rememberTextFieldState(),
            endIcon = Icons.Default.Email,
            hint = "example@test.com",
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}