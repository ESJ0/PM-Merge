
package com.esjoprueba.lab8.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.esjoprueba.lab8.data.Character

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<Character>)

    @Query("SELECT * FROM characters")
    suspend fun getAll(): List<Character>

    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getById(id: Int): Character?
}
