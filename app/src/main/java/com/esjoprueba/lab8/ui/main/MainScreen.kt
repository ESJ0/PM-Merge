package com.esjoprueba.lab8.ui.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.esjoprueba.lab8.navigation.*
import com.esjoprueba.lab8.ui.characters.CharactersScreen
import com.esjoprueba.lab8.ui.details.CharacterDetailsScreen
import com.esjoprueba.lab8.ui.details.LocationDetailsScreen
import com.esjoprueba.lab8.ui.locations.LocationsScreen
import com.esjoprueba.lab8.ui.profile.ProfileScreen

@Composable
fun MainScreen(onLogout: () -> Unit) {
    val navController = rememberNavController()
    val context = LocalContext.current
    var selectedItem by remember { mutableIntStateOf(0) }

    // Manejar el back button cuando estamos en la lista de personajes
    BackHandler(enabled = navController.currentDestination?.route == CharactersRoute::class.qualifiedName) {
        // Cerrar la aplicación
        (context as? androidx.activity.ComponentActivity)?.finish()
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Characters"
                        )
                    },
                    label = { Text("Characters") },
                    selected = selectedItem == 0,
                    onClick = {
                        selectedItem = 0
                        navController.navigate(CharactersRoute) {
                            popUpTo(CharactersRoute) { inclusive = true }
                        }
                    }
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Place,
                            contentDescription = "Locations"
                        )
                    },
                    label = { Text("Locations") },
                    selected = selectedItem == 1,
                    onClick = {
                        selectedItem = 1
                        navController.navigate(LocationsRoute) {
                            popUpTo(CharactersRoute)
                        }
                    }
                )
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile"
                        )
                    },
                    label = { Text("Profile") },
                    selected = selectedItem == 2,
                    onClick = {
                        selectedItem = 2
                        navController.navigate(ProfileRoute) {
                            popUpTo(CharactersRoute)
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = CharactersRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Pantalla de personajes
            composable<CharactersRoute> {
                CharactersScreen(
                    onCharacterClick = { characterId ->
                        navController.navigate(CharacterDetailsRoute(characterId))
                    }
                )
            }

            // Pantalla de ubicaciones
            composable<LocationsRoute> {
                LocationsScreen(
                    onLocationClick = { locationId ->
                        navController.navigate(LocationDetailsRoute(locationId))
                    }
                )
            }

            // Detalles de personaje
            composable<CharacterDetailsRoute> { backStackEntry ->
                val args = backStackEntry.toRoute<CharacterDetailsRoute>()
                CharacterDetailsScreen(
                    characterId = args.characterId,
                    onBackClick = { navController.popBackStack() }
                )
            }

            // Detalles de ubicación
            composable<LocationDetailsRoute> { backStackEntry ->
                val args = backStackEntry.toRoute<LocationDetailsRoute>()
                LocationDetailsScreen(
                    locationId = args.locationId,
                    onBackClick = { navController.popBackStack() }
                )
            }

            // Perfil
            composable<ProfileRoute> {
                ProfileScreen(onLogout = onLogout)
            }
        }
    }
}