package com.esjoprueba.lab8.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.esjoprueba.lab8.ui.characters.CharactersScreen
import com.esjoprueba.lab8.ui.details.CharacterDetailsScreen
import com.esjoprueba.lab8.ui.details.LocationDetailsScreen
import com.esjoprueba.lab8.ui.locations.LocationsScreen
import com.esjoprueba.lab8.ui.profile.ProfileScreen

@Composable
fun MainScreen(onLogout: () -> Unit) {
    val navController = rememberNavController()
    var selectedItem by remember { mutableIntStateOf(0) }

    val items = listOf("Characters", "Locations", "Profile")

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
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            when (index) {
                                0 -> navController.navigate("characters")
                                1 -> navController.navigate("locations")
                                2 -> navController.navigate("profile")
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "characters",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("characters") {
                CharactersScreen(
                    onCharacterClick = { characterId ->
                        navController.navigate("character_details/$characterId")
                    }
                )
            }
            composable("locations") {
                LocationsScreen(
                    onLocationClick = { locationId ->
                        navController.navigate("location_details/$locationId")
                    }
                )
            }

            composable(
                route = "character_details/{characterId}"
            ) { backStackEntry ->
                val characterId = backStackEntry.arguments?.getString("characterId")?.toIntOrNull()
                CharacterDetailsScreen(characterId = characterId, onBackClick = { navController.popBackStack() })
            }

            composable(
                route = "location_details/{locationId}"
            ) { backStackEntry ->
                val locationId = backStackEntry.arguments?.getString("locationId")?.toIntOrNull()
                LocationDetailsScreen(locationId = locationId, onBackClick = { navController.popBackStack() })
            }

            composable("profile") {
                ProfileScreen(onLogout = onLogout)
            }
        }
    }
}
