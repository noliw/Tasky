package com.nolawiworkineh.designsystem.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TaskyBackground(
    blackHeightFraction: Float = 0.16f, // Default to 16% height for the black container
    content: @Composable () -> Unit, // Slot for dynamic content
    toolbar: @Composable () -> Unit, // Slot for toolbar composable
    isBlackToolBar: Boolean = true // Determines whether toolbar is black
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Toolbar container
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isBlackToolBar) Color.Black else Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(blackHeightFraction) // Dynamic height based on fraction
                    .background(if (isBlackToolBar) Color.Black else Color.White)
            ) {
                toolbar() // Custom toolbar provided by the user
            }

            if (!isBlackToolBar) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp), // Fills the width of the Box
                    thickness = 2.dp,   // Border thickness
                    color = Color.Gray // Border color
                )
            }


            // White container with customizable rounded corners drawn on top of the toolbar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(Color.White)
                // Align this white container at the bottom
            ) {
                content() // Inject dynamic content into the white container
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskyBackgroundPreview() {
    TaskyBackground(
         // Black toolbar takes up 20% of the screen height
        isBlackToolBar = true, // Set the toolbar to be black
        toolbar = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                // Custom toolbar content for the preview
                Text(
                    text = "Toolbar",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,

                )
            }
        },
        content = {
            // Custom content for the preview
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
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