package com.esjoprueba.lab8.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class RickAndMortyApiService {

    private val baseUrl = "https://rickandmortyapi.com/api"

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    suspend fun getCharacters(): Result<CharacterResponse> = withContext(Dispatchers.IO) {
        try {
            val url = URL("$baseUrl/character")
            val connection = url.openConnection() as HttpURLConnection

            connection.apply {
                requestMethod = "GET"
                connectTimeout = 10000
                readTimeout = 10000
                setRequestProperty("Content-Type", "application/json")
            }

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = BufferedReader(InputStreamReader(connection.inputStream))
                    .use { it.readText() }

                val characterResponse = json.decodeFromString<CharacterResponse>(response)
                Result.success(characterResponse)
            } else {
                Result.failure(Exception("HTTP error: $responseCode"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getLocations(): Result<LocationResponse> = withContext(Dispatchers.IO) {
        try {
            val url = URL("$baseUrl/location")
            val connection = url.openConnection() as HttpURLConnection

            connection.apply {
                requestMethod = "GET"
                connectTimeout = 10000
                readTimeout = 10000
                setRequestProperty("Content-Type", "application/json")
            }

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = BufferedReader(InputStreamReader(connection.inputStream))
                    .use { it.readText() }

                val locationResponse = json.decodeFromString<LocationResponse>(response)
                Result.success(locationResponse)
            } else {
                Result.failure(Exception("HTTP error: $responseCode"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: RickAndMortyApiService? = null

        fun getInstance(): RickAndMortyApiService {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: RickAndMortyApiService().also { INSTANCE = it }
            }
        }
    }
}