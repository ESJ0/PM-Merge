package com.esjoprueba.lab8.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.esjoprueba.lab8.navigation.*
import com.esjoprueba.lab8.ui.characters.CharactersScreen
import com.esjoprueba.lab8.ui.details.CharacterDetailsScreen
import com.esjoprueba.lab8.ui.details.LocationDetailsScreen
import com.esjoprueba.lab8.ui.locations.LocationsScreen
import com.esjoprueba.lab8.ui.profile.ProfileScreen

@Composable
fun MainScreen(onLogout: () -> Unit) {
    val navController = rememberNavController()
    var selectedItem by remember { mutableIntStateOf(0) }

    val items = listOf(
        CharactersRoute.route,
        LocationsRoute.route,
        ProfileRoute.route
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = when (index) {
                                    0 -> Icons.Default.Person
                                    1 -> Icons.Default.Place
                                    else -> Icons.Default.AccountCircle
                                },
                                contentDescription = item
                            )
                        },
                        label = { Text(item.replaceFirstChar { it.uppercase() }) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            when (index) {
                                0 -> navController.navigate(CharactersRoute.route)
                                1 -> navController.navigate(LocationsRoute.route)
                                2 -> navController.navigate(ProfileRoute.route)
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = CharactersRoute.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Pantalla de personajes
            composable(CharactersRoute.route) {
                CharactersScreen(
                    onCharacterClick = { characterId ->
                        navController.navigate(CharacterDetailsRoute(characterId).toRoute())
                    }
                )
            }

            // Pantalla de ubicaciones
            composable(LocationsRoute.route) {
                LocationsScreen(
                    onLocationClick = { locationId ->
                        navController.navigate(LocationDetailsRoute(locationId).toRoute())
                    }
                )
            }

            // Detalles de personaje
            composable("character_details/{characterId}") { backStackEntry ->
                val characterId =
                    backStackEntry.arguments?.getString("characterId")?.toIntOrNull()
                CharacterDetailsScreen(
                    characterId = characterId,
                    onBackClick = { navController.popBackStack() }
                )
            }

            // Detalles de ubicación
            composable("location_details/{locationId}") { backStackEntry ->
                val locationId =
                    backStackEntry.arguments?.getString("locationId")?.toIntOrNull()
                LocationDetailsScreen(
                    locationId = locationId,
                    onBackClick = { navController.popBackStack() }
                )
            }

            // Perfil
            composable(ProfileRoute.route) {
                ProfileScreen(onLogout = onLogout)
            }
        }
    }
}
