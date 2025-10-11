package com.esjoprueba.lab8.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.esjoprueba.lab8.data.dao.CharacterDao
import com.esjoprueba.lab8.data.dao.LocationDao
import com.esjoprueba.lab8.data.Character
import com.esjoprueba.lab8.data.Location


@Database(
    entities = [Character::class, Location::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun locationDao(): LocationDao
}
