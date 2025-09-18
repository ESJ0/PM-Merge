package com.esjoprueba.lab8.navigation

import kotlinx.serialization.Serializable

@Serializable
object LoginRoute {
    const val route = "login"
}

@Serializable
object MainRoute {
    const val route = "main"
}

@Serializable
object CharactersRoute {
    const val route = "characters"
}

@Serializable
object LocationsRoute {
    const val route = "locations"
}

@Serializable
object ProfileRoute {
    const val route = "profile"
}

@Serializable
data class CharacterDetailsRoute(val characterId: Int) {
    fun toRoute(): String = "character_details/$characterId"
}

@Serializable
data class LocationDetailsRoute(val locationId: Int) {
    fun toRoute(): String = "location_details/$locationId"
}
