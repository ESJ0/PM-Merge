package com.esjoprueba.lab8.data

import android.annotation.SuppressLint
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
@Entity(tableName = "characters")
data class Character(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String
)