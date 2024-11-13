package com.nolawiworkineh.designsystem.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nolawiworkineh.designsystem.Theme.TaskyTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskyDatePicker(
    selectedDate: LocalDate,
    isVisible: Boolean,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    if (isVisible) {
        val initialMillis = selectedDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = initialMillis
        )

        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val selectedLocalDate = Instant
                                .ofEpochMilli(millis)
                                .atZone(ZoneOffset.UTC)
                                .toLocalDate()
                            onDateSelected(selectedLocalDate)
                            onDismiss()
                        }
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false
            )
        }
    }
}

// Optional: Create a helper function to format the date
fun LocalDate.formatMonth(): String {
    return this.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
}

@Preview( showSystemUi = true)
@Composable
private fun TaskyDatePickerPreviewCustomDate() {
    TaskyTheme {
        TaskyDatePicker(
            selectedDate = LocalDate.of(2024, 3, 5), // March 5, 2024
            isVisible = true,
            onDateSelected = {},
            onDismiss = {}
        )
    }
}
