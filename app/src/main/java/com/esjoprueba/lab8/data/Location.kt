package com.esjoprueba.lab8.data

import android.annotation.SuppressLint
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
@Entity(tableName = "locations")
data class Location(
    @PrimaryKey val id: Int,
    val name: String,
    val type: String,
    val dimension: String
)