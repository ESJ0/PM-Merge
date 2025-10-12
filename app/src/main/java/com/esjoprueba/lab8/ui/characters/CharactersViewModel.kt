package com.esjoprueba.lab8.ui.characters

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.esjoprueba.lab8.data.repository.CharacterRepository
import com.esjoprueba.lab8.data.room.DatabaseProvider
import com.esjoprueba.lab8.ui.states.CharactersUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class CharactersViewModel(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharactersUiState())
    val uiState: StateFlow<CharactersUiState> = _uiState.asStateFlow()

    init {
        loadCharacters()
    }

    private fun loadCharacters() {
        viewModelScope.launch {
            _uiState.value = CharactersUiState(isLoading = true)

            // Simular loading de 4 segundos
            delay(4000)

            // Generar número aleatorio del 1 al 10
            val randomNumber = Random.nextInt(1, 11)

            if (randomNumber % 2 == 0) {
                // Número par - mostrar data desde Room
                try {
                    val characters = characterRepository.getAllCharacters()
                    _uiState.value = CharactersUiState(
                        isLoading = false,
                        data = characters,
                        hasError = false
                    )
                } catch (_: Exception) {
                    _uiState.value = CharactersUiState(
                        isLoading = false,
                        data = emptyList(),
                        hasError = true
                    )
                }
            } else {
                // Número impar - mostrar error
                _uiState.value = CharactersUiState(
                    isLoading = false,
                    data = emptyList(),
                    hasError = true
                )
            }
        }
    }

    fun retry() {
        loadCharacters()
    }
}

class CharactersViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharactersViewModel::class.java)) {
            val db = DatabaseProvider.getDatabase(context)
            val characterRepo = CharacterRepository(db.characterDao())
            @Suppress("UNCHECKED_CAST")
            return CharactersViewModel(characterRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}