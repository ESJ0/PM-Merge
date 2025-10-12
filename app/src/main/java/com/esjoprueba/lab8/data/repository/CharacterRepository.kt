package com.esjoprueba.lab8.data.repository

import com.esjoprueba.lab8.data.Character
import com.esjoprueba.lab8.data.CharacterDb
import com.esjoprueba.lab8.data.dao.CharacterDao

class CharacterRepository(private val characterDao: CharacterDao) {

    suspend fun syncCharacters() {
        val characters = CharacterDb.getCharacters()
        characterDao.insertAll(characters)
    }

    suspend fun getAllCharacters(): List<Character> {
        return characterDao.getAll()
    }

    suspend fun getCharacterById(id: Int): Character? {
        return characterDao.getById(id)
    }
}