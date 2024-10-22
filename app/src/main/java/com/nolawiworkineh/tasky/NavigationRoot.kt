package com.nolawiworkineh.tasky

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nolawiworkineh.auth.presentation.register.RegisterScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = "authFeature"
    ) {
        authGraph(navController)
    }
}

// Authentication Graph
private fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation(
        startDestination = "registerScreen",
        route = "authFeature"
    ) {
        composable("registerScreen") {
            // Navigate to RegisterScreen
            RegisterScreenRoot(
                OnNavigateBackToLoginClick = {
                    navController.navigate("loginScreen")
                }
            )
        }
        composable("loginScreen") {
                    Text("Login")
        }
    }
}
