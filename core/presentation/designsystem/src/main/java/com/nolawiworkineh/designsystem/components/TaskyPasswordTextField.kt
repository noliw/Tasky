package com.nolawiworkineh.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.rememberTextFieldState
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nolawiworkineh.designsystem.Theme.EyeClosedIcon
import com.nolawiworkineh.designsystem.Theme.EyeOpenIcon
import com.nolawiworkineh.designsystem.Theme.TaskyBlack
import com.nolawiworkineh.designsystem.Theme.TaskyGray
import com.nolawiworkineh.designsystem.Theme.TaskyGreen
import com.nolawiworkineh.designsystem.Theme.TaskyLightGray
import com.nolawiworkineh.designsystem.Theme.TaskyRed
import com.nolawiworkineh.designsystem.Theme.TaskyTextGray
import com.nolawiworkineh.designsystem.Theme.TaskyTheme

@Composable
fun TaskyPasswordTextField(
    state: TextFieldState,
    hint: String,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
) {
    var isFocused by remember { mutableStateOf(false) }

    val shape = RoundedCornerShape(8.dp)

    val borderColor = when {
        !isError && !isFocused && state.text.isNotEmpty() -> TaskyGreen
        isError && !isFocused && state.text.isNotEmpty() -> TaskyRed
        isFocused -> TaskyTextGray
        else -> TaskyLightGray
    }

    BasicSecureTextField(
        state = state,
        textObfuscationMode = if (isPasswordVisible) {
            TextObfuscationMode.Visible
        } else {
            TextObfuscationMode.Hidden
        },
        textStyle = LocalTextStyle.current.copy(
            color = TaskyBlack
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
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
            },
        decorator = { innerBox ->
            Row(
                modifier = Modifier.fillMaxWidth(),
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
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = if (isPasswordVisible) EyeOpenIcon else EyeClosedIcon,
                    contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                    tint = TaskyTextGray,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable(onClick = onTogglePasswordVisibility)
                )
            }
        }
    )
}

@Preview
@Composable
private fun TaskyPasswordTextFieldPreview() {
    TaskyTheme {
        TaskyPasswordTextField(
            state = rememberTextFieldState(),
            hint = "Password",
            modifier = Modifier.fillMaxWidth(),
            isPasswordVisible = false,
            onTogglePasswordVisibility = {}
        )
    }
}






@Preview
@Composable
private fun PacePalTextFieldPreview() {
    TaskyTheme {
        TaskyPasswordTextField(
            state = rememberTextFieldState(),
            hint = "password",
            modifier = Modifier
                .fillMaxWidth(),
            isPasswordVisible = false,
            onTogglePasswordVisibility = {}
        )
    }
}
