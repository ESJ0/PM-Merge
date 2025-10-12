package com.esjoprueba.lab8.data.repository

import com.esjoprueba.lab8.data.Location
import com.esjoprueba.lab8.data.LocationDb
import com.esjoprueba.lab8.data.dao.LocationDao

class LocationRepository(private val locationDao: LocationDao) {

    suspend fun syncLocations() {
        val locations = LocationDb.getLocations()
        locationDao.insertAll(locations)
    }

    suspend fun getAllLocations(): List<Location> {
        return locationDao.getAll()
    }

    suspend fun getLocationById(id: Int): Location? {
        return locationDao.getById(id)
    }
}