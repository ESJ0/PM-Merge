package com.esjoprueba.lab8.data

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@InternalSerializationApi @Serializable
data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String
)