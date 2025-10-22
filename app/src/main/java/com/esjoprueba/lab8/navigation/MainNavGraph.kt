package com.esjoprueba.lab8.navigation

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
import com.esjoprueba.lab8.data.repository.UserPreferencesRepository
import com.esjoprueba.lab8.ui.characters.CharactersScreen
import com.esjoprueba.lab8.ui.details.CharacterDetailsScreen
import com.esjoprueba.lab8.ui.details.LocationDetailsScreen
import com.esjoprueba.lab8.ui.locations.LocationsScreen
import com.esjoprueba.lab8.ui.login.LoginScreen
import com.esjoprueba.lab8.ui.profile.ProfileScreen

@Composable
fun MainNavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val userPreferencesRepository = UserPreferencesRepository.getInstance(context)
    val userName by userPreferencesRepository.userName.collectAsState(initial = null)

    // Verificar si el usuario ya está loggeado al iniciar
    LaunchedEffect(userName) {
        if (userName != null && navController.currentDestination?.route == LoginRoute::class.qualifiedName) {
            navController.navigate(MainRoute) {
                popUpTo<LoginRoute> { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = LoginRoute
    ) {
        // Pantalla de login
        composable<LoginRoute> {
            // Evitar que el usuario regrese desde el login si ya cerró sesión
            BackHandler {
                // Cerrar la aplicación
                (context as? androidx.activity.ComponentActivity)?.finish()
            }

            LoginScreen(
                onStartClick = {
                    navController.navigate(MainRoute) {
                        popUpTo<LoginRoute> { inclusive = true }
                    }
                }
            )
        }

        // Pantalla principal con navegación nested
        composable<MainRoute> {
            MainScreenWithNavigation(
                onLogout = {
                    navController.navigate(LoginRoute) {
                        popUpTo<MainRoute> { inclusive = true }
                    }
                }
            )
        }
    }
}

@Composable
private fun MainScreenWithNavigation(onLogout: () -> Unit) {
    val nestedNavController = rememberNavController()
    val context = LocalContext.current
    var selectedItem by remember { mutableIntStateOf(0) }

    // Manejar el back button cuando estamos en la lista de personajes
    BackHandler(enabled = nestedNavController.currentDestination?.route == CharactersRoute::class.qualifiedName) {
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
                        nestedNavController.navigate(CharactersRoute) {
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
                        nestedNavController.navigate(LocationsRoute) {
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
                        nestedNavController.navigate(ProfileRoute) {
                            popUpTo(CharactersRoute)
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = nestedNavController,
            startDestination = CharactersRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Pantalla de personajes
            composable<CharactersRoute> {
                CharactersScreen(
                    onCharacterClick = { characterId ->
                        nestedNavController.navigate(CharacterDetailsRoute(characterId))
                    }
                )
            }

            // Pantalla de ubicaciones
            composable<LocationsRoute> {
                LocationsScreen(
                    onLocationClick = { locationId ->
                        nestedNavController.navigate(LocationDetailsRoute(locationId))
                    }
                )
            }

            // Detalles de personaje
            composable<CharacterDetailsRoute> { backStackEntry ->
                val args = backStackEntry.toRoute<CharacterDetailsRoute>()
                CharacterDetailsScreen(
                    characterId = args.characterId,
                    onBackClick = { nestedNavController.popBackStack() }
                )
            }

            // Detalles de ubicación
            composable<LocationDetailsRoute> { backStackEntry ->
                val args = backStackEntry.toRoute<LocationDetailsRoute>()
                LocationDetailsScreen(
                    locationId = args.locationId,
                    onBackClick = { nestedNavController.popBackStack() }
                )
            }

            // Perfil
            composable<ProfileRoute> {
                ProfileScreen(onLogout = onLogout)
            }
        }
    }
}