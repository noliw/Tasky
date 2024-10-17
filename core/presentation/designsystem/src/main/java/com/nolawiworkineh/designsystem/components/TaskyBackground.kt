package com.nolawiworkineh.designsystem.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nolawiworkineh.designsystem.Theme.TaskyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskyBackground(
    paddingValues: PaddingValues = PaddingValues(),
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Entire screen background is black
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = paddingValues.calculateTopPadding()) // Offset by TopAppBar height
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)) // Apply rounded corners
                .background(Color.White) // White background
        ) {
            content() // Place your content here
        }
    }
}



@Preview(showBackground = true)
@Composable
private fun TaskyBackgroundPreview() {
    TaskyTheme {
        TaskyBackground(
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Welcome to Tasky!",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.Black
                    )
                }
            }
        )
    }
}