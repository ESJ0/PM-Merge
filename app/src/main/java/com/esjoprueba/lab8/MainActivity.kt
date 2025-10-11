package com.esjoprueba.lab8

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.esjoprueba.lab8.data.room.DatabaseProvider
import com.esjoprueba.lab8.data.Character
import com.esjoprueba.lab8.data.Location
import com.esjoprueba.lab8.navigation.MainNavGraph
import com.esjoprueba.lab8.ui.theme.RickAndMortyAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔹 Insertar datos de prueba en Room
        lifecycleScope.launch {
            val db = DatabaseProvider.getDatabase(this@MainActivity)

            // Insertar personajes de prueba
            db.characterDao().insertAll(
                listOf(
                    Character(1, "Rick Sanchez", "Alive", "Human", "Male", "https://rickandmortyapi.com/api/character/avatar/1.jpeg"),
                    Character(2, "Morty Smith", "Alive", "Human", "Male", "https://rickandmortyapi.com/api/character/avatar/2.jpeg"),
                    Character(3, "Summer Smith", "Alive", "Human", "Female", "https://rickandmortyapi.com/api/character/avatar/3.jpeg"),
                    Character(4, "Beth Smith", "Alive", "Human", "Female", "https://rickandmortyapi.com/api/character/avatar/4.jpeg"),
                    Character(5, "Jerry Smith", "Alive", "Human", "Male", "https://rickandmortyapi.com/api/character/avatar/5.jpeg")
                )
            )

            // Insertar ubicaciones de prueba
            db.locationDao().insertAll(
                listOf(
                    Location(1, "Earth (C-137)", "Planet", "Dimension C-137"),
                    Location(2, "Citadel of Ricks", "Space station", "Unknown"),
                    Location(3, "Anatomy Park", "Microverse", "Dimension C-500A"),
                    Location(4, "Bird World", "Planet", "Unknown"),
                    Location(5, "Interdimensional Cable", "TV Network", "Unknown")
                )
            )
        }

        setContent {
            RickAndMortyAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavGraph()
                }
            }
        }
    }
}
