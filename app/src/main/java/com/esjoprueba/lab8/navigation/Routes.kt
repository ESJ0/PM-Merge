package com.esjoprueba.lab8.navigation

import kotlinx.serialization.Serializable

@Serializable
object LoginRoute

@Serializable
object MainRoute

@Serializable
object CharactersRoute

@Serializable
object LocationsRoute

@Serializable
object ProfileRoute

@Serializable
data class CharacterDetailsRoute(val characterId: Int)

@Serializable
data class LocationDetailsRoute(val locationId: Int)
