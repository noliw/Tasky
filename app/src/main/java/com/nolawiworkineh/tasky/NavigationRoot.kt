package com.nolawiworkineh.tasky

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nolawiworkineh.agenda.presentation.AgendaScreenRoutes
import com.nolawiworkineh.agenda.presentation.HomeScreen
import com.nolawiworkineh.auth.presentation.AuthScreenRoutes
import com.nolawiworkineh.auth.presentation.login.LoginScreenRoot
import com.nolawiworkineh.auth.presentation.register.RegisterScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = AuthScreenRoutes.RegisterScreen
    ) {
        authGraph(navController)
        agendaGraph(navController)
    }
}

// Authentication Graph
private fun NavGraphBuilder.authGraph(navController: NavHostController) {

    composable<AuthScreenRoutes.RegisterScreen> {
        // Navigate to RegisterScreen
        RegisterScreenRoot(
            navigateBackToLoginClick = {
                navController.navigate(AuthScreenRoutes.LoginScreen) {
                    popUpTo(AuthScreenRoutes.RegisterScreen) {
                        inclusive = true
                        saveState = true
                    }
                    restoreState = true
                }
            }
        )
    }
    composable<AuthScreenRoutes.LoginScreen> {
        LoginScreenRoot(
            navigateToRegisterScreen = {
                navController.navigate(AuthScreenRoutes.RegisterScreen) {
                    popUpTo(AuthScreenRoutes.LoginScreen) {
                        inclusive = true
                        saveState = true
                    }
                    restoreState = true
                }

            },
            navigateToHomeScreen = {
                navController.navigate(AgendaScreenRoutes.HomeScreen) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }

            }
        )
    }

}


private fun NavGraphBuilder.agendaGraph(navController: NavHostController) {
    composable<AgendaScreenRoutes.HomeScreen> {
        HomeScreen()
    }


}

