package com.esjoprueba.lab8.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.esjoprueba.lab8.data.Location

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locations: List<Location>)

    @Query("SELECT * FROM locations")
    suspend fun getAll(): List<Location>
}
