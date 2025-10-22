package com.esjoprueba.lab8.data.repository

import com.esjoprueba.lab8.data.Character
import com.esjoprueba.lab8.data.dao.CharacterDao
import com.esjoprueba.lab8.data.mappers.toCharacterEntityList
import com.esjoprueba.lab8.data.network.RickAndMortyApiService

class CharacterRepository(
    private val characterDao: CharacterDao,
    private val apiService: RickAndMortyApiService = RickAndMortyApiService.getInstance()
) {

    /**
     * Offline First: Intenta obtener personajes de la base de datos local.
     * Si no hay datos, hace llamada al API y persiste los resultados.
     */
    suspend fun getAllCharacters(): Result<List<Character>> {
        return try {
            // 1. Intentar obtener de Room primero
            val localCharacters = characterDao.getAll()

            if (localCharacters.isNotEmpty()) {
                // Si hay data local, retornarla
                Result.success(localCharacters)
            } else {
                // 2. Si no hay data local, llamar al API
                val apiResult = apiService.getCharacters()

                apiResult.fold(
                    onSuccess = { response ->
                        // 3. Mapear DTOs a Entities
                        val characters = response.results.toCharacterEntityList()

                        // 4. Persistir en Room
                        characterDao.insertAll(characters)

                        // 5. Retornar los datos
                        Result.success(characters)
                    },
                    onFailure = { exception ->
                        Result.failure(exception)
                    }
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Obtiene un personaje por ID desde Room
     */
    suspend fun getCharacterById(id: Int): Character? {
        return characterDao.getById(id)
    }

    /**
     * Fuerza la actualización de personajes desde el API
     */
    suspend fun refreshCharacters(): Result<List<Character>> {
        return try {
            val apiResult = apiService.getCharacters()

            apiResult.fold(
                onSuccess = { response ->
                    val characters = response.results.toCharacterEntityList()
                    characterDao.insertAll(characters)
                    Result.success(characters)
                },
                onFailure = { exception ->
                    Result.failure(exception)
                }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}