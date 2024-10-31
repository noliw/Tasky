package com.nolawiworkineh.auth.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nolawiworkineh.designsystem.Theme.TaskyBlack
import com.nolawiworkineh.designsystem.Theme.TaskyWhite

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    Column (
        modifier = modifier.fillMaxSize().background(TaskyBlack),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Login", color = TaskyWhite)

    }
}