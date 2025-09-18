package com.esjoprueba.lab8.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.esjoprueba.lab8.ui.login.LoginScreen
import com.esjoprueba.lab8.ui.main.MainScreen

@Composable
fun MainNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginRoute.route
    ) {
        // Pantalla de login
        composable(LoginRoute.route) {
            LoginScreen(
                onStartClick = {
                    navController.navigate(MainRoute.route) {
                        popUpTo(LoginRoute.route) { inclusive = true }
                    }
                }
            )
        }

        // Pantalla principal
        composable(MainRoute.route) {
            MainScreen(
                onLogout = {
                    navController.navigate(LoginRoute.route) {
                        popUpTo(MainRoute.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
