package com.esjoprueba.lab8.data.repository

import com.esjoprueba.lab8.data.Location
import com.esjoprueba.lab8.data.dao.LocationDao
import com.esjoprueba.lab8.data.mappers.toLocationEntityList
import com.esjoprueba.lab8.data.network.RickAndMortyApiService

class LocationRepository(
    private val locationDao: LocationDao,
    private val apiService: RickAndMortyApiService = RickAndMortyApiService.getInstance()
) {

    /**
     * Offline First: Intenta obtener ubicaciones de la base de datos local.
     * Si no hay datos, hace llamada al API y persiste los resultados.
     */
    suspend fun getAllLocations(): Result<List<Location>> {
        return try {
            // 1. Intentar obtener de Room primero
            val localLocations = locationDao.getAll()

            if (localLocations.isNotEmpty()) {
                // Si hay data local, retornarla
                Result.success(localLocations)
            } else {
                // 2. Si no hay data local, llamar al API
                val apiResult = apiService.getLocations()

                apiResult.fold(
                    onSuccess = { response ->
                        // 3. Mapear DTOs a Entities
                        val locations = response.results.toLocationEntityList()

                        // 4. Persistir en Room
                        locationDao.insertAll(locations)

                        // 5. Retornar los datos
                        Result.success(locations)
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
     * Obtiene una ubicación por ID desde Room
     */
    suspend fun getLocationById(id: Int): Location? {
        return locationDao.getById(id)
    }

    /**
     * Fuerza la actualización de ubicaciones desde el API
     */
    suspend fun refreshLocations(): Result<List<Location>> {
        return try {
            val apiResult = apiService.getLocations()

            apiResult.fold(
                onSuccess = { response ->
                    val locations = response.results.toLocationEntityList()
                    locationDao.insertAll(locations)
                    Result.success(locations)
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