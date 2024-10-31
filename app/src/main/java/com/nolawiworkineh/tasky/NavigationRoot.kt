package com.nolawiworkineh.tasky

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nolawiworkineh.auth.presentation.AuthScreenRoutes
import com.nolawiworkineh.auth.presentation.register.RegisterScreenRoot
import com.nolawiworkineh.auth.presentation.login.LoginScreen

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = AuthScreenRoutes.RegisterScreen
    ) {
        authGraph(navController)
    }
}

// Authentication Graph
private fun NavGraphBuilder.authGraph(navController: NavHostController) {

    composable<AuthScreenRoutes.RegisterScreen> {
        // Navigate to RegisterScreen
        RegisterScreenRoot(
            navigateBackToLoginClick = {
                navController.navigate(AuthScreenRoutes.LoginScreen)
            }
        )
    }
    composable<AuthScreenRoutes.LoginScreen> {
        LoginScreen()
    }

}
