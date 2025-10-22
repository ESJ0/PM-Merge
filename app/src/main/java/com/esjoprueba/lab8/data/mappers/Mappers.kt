package com.esjoprueba.lab8.data.mappers

import com.esjoprueba.lab8.data.Character
import com.esjoprueba.lab8.data.Location
import com.esjoprueba.lab8.data.network.CharacterDto
import com.esjoprueba.lab8.data.network.LocationDto

// Mapper de CharacterDto a Character Entity
fun CharacterDto.toEntity(): Character {
    return Character(
        id = this.id,
        name = this.name,
        status = this.status,
        species = this.species,
        gender = this.gender,
        image = this.image
    )
}

fun List<CharacterDto>.toCharacterEntityList(): List<Character> {
    return this.map { it.toEntity() }
}

// Mapper de LocationDto a Location Entity
fun LocationDto.toEntity(): Location {
    return Location(
        id = this.id,
        name = this.name,
        type = this.type,
        dimension = this.dimension
    )
}

fun List<LocationDto>.toLocationEntityList(): List<Location> {
    return this.map { it.toEntity() }
}