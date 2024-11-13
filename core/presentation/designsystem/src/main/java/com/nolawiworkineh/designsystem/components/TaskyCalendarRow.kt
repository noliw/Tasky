package com.nolawiworkineh.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nolawiworkineh.designsystem.Theme.TaskyBlack
import com.nolawiworkineh.designsystem.Theme.TaskyGray
import com.nolawiworkineh.designsystem.Theme.TaskyTheme
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@Composable
fun CalendarDaysRow(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {

    val sundayOfWeek = selectedDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (day in 0..6) {
            val dayDate = sundayOfWeek.plusDays(day.toLong())
            val isSelected = dayDate.isEqual(selectedDate)

            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(if (isSelected) Color(0xFFFDEFA8) else Color.Transparent)
                    .padding(16.dp)
                    .clickable { onDateSelected(dayDate) },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Day of week (S, M, T, etc)
                Text(
                    text = dayDate.dayOfWeek.name.first().toString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = TaskyGray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                // Day number
                Text(
                    text = dayDate.dayOfMonth.toString(),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = TaskyBlack,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarDaysRowPreview() {
    TaskyTheme {
        CalendarDaysRow(
            selectedDate = LocalDate.of(2024, 3, 5), // March 5, 2024 (Tuesday)
            onDateSelected = { }
        )
    }
}