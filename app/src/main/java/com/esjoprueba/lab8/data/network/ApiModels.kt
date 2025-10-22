package com.esjoprueba.lab8.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// DTOs para Characters
@Serializable
data class CharacterResponse(
    val info: PageInfo,
    val results: List<CharacterDto>
)

@Serializable
data class CharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String
)

// DTOs para Locations
@Serializable
data class LocationResponse(
    val info: PageInfo,
    val results: List<LocationDto>
)

@Serializable
data class LocationDto(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String
)

@Serializable
data class PageInfo(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)