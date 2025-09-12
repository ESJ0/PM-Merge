package com.esjoprueba.lab8.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.esjoprueba.lab8.ui.characters.CharactersScreen
import com.esjoprueba.lab8.ui.details.CharacterDetailsScreen
import com.esjoprueba.lab8.ui.login.LoginScreen

@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onStartClick = {
                    navController.navigate("characters") {
                        // Esto elimina Login del backstack
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("characters") {
            CharactersScreen(
                onCharacterClick = { characterId ->
                    navController.navigate("character_details/$characterId")
                }
            )
        }

        composable("character_details/{characterId}") { backStackEntry ->
            val characterId = backStackEntry.arguments?.getString("characterId")?.toIntOrNull()
            CharacterDetailsScreen(
                characterId = characterId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}