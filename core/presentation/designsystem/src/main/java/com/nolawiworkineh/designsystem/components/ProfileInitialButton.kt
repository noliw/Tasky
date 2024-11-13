package com.nolawiworkineh.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nolawiworkineh.designsystem.Theme.TaskyGray
import com.nolawiworkineh.designsystem.Theme.TaskyLightGray
import com.nolawiworkineh.designsystem.Theme.TaskyTheme

@Composable
private fun ProfileIcon(
    initials: String,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier,
    ) {
    Box(
        modifier = modifier
            .size(45.dp)
            .clip(CircleShape)
            .background(TaskyLightGray)
            .clickable { onProfileClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            color = TaskyGray,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
        )
    }
    
}

@Preview
@Composable
fun ProfileIconPreviewVariations() {
    TaskyTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileIcon(
                initials = "NW",
                onProfileClick = {},
                )
        }
    }
}