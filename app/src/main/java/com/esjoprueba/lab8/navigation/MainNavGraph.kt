package com.esjoprueba.lab8.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.esjoprueba.lab8.data.repository.UserPreferencesRepository
import com.esjoprueba.lab8.ui.login.LoginScreen
import com.esjoprueba.lab8.ui.main.MainScreen

@Composable
fun MainNavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val userPreferencesRepository = UserPreferencesRepository.getInstance(context)
    val userName by userPreferencesRepository.userName.collectAsState(initial = null)

    // Verificar si el usuario ya está loggeado al iniciar
    LaunchedEffect(userName) {
        if (userName != null && navController.currentDestination?.route == LoginRoute.route) {
            navController.navigate(MainRoute.route) {
                popUpTo(LoginRoute.route) { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = LoginRoute.route
    ) {
        // Pantalla de login
        composable(LoginRoute.route) {
            // Evitar que el usuario regrese desde el login si ya cerró sesión
            BackHandler {
                // Cerrar la aplicación
                (context as? androidx.activity.ComponentActivity)?.finish()
            }

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