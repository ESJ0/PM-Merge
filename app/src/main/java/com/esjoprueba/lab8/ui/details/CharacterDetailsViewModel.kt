package com.esjoprueba.lab8.ui.details

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.esjoprueba.lab8.data.repository.CharacterRepository
import com.esjoprueba.lab8.data.room.DatabaseProvider
import com.esjoprueba.lab8.ui.states.CharacterDetailsUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class CharacterDetailsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterDetailsUiState())
    val uiState: StateFlow<CharacterDetailsUiState> = _uiState.asStateFlow()

    init {
        loadCharacterDetails()
    }

    private fun loadCharacterDetails() {
        viewModelScope.launch {
            _uiState.value = CharacterDetailsUiState(isLoading = true)

            // Simular loading de 2 segundos
            delay(2000)

            // Generar número aleatorio del 1 al 10
            val randomNumber = Random.nextInt(1, 11)

            if (randomNumber % 2 == 0) {
                // Número par - mostrar data desde Room
                try {
                    val characterId = savedStateHandle.get<String>("characterId")?.toIntOrNull()
                    val character = if (characterId != null) {
                        characterRepository.getCharacterById(characterId)
                    } else null

                    _uiState.value = CharacterDetailsUiState(
                        isLoading = false,
                        data = character,
                        hasError = false
                    )
                } catch (_: Exception) {
                    _uiState.value = CharacterDetailsUiState(
                        isLoading = false,
                        data = null,
                        hasError = true
                    )
                }
            } else {
                // Número impar - mostrar error
                _uiState.value = CharacterDetailsUiState(
                    isLoading = false,
                    data = null,
                    hasError = true
                )
            }
        }
    }

    fun retry() {
        loadCharacterDetails()
    }
}

class CharacterDetailsViewModelFactory(
    private val context: Context,
    private val savedStateHandle: SavedStateHandle
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterDetailsViewModel::class.java)) {
            val db = DatabaseProvider.getDatabase(context)
            val characterRepo = CharacterRepository(db.characterDao())
            @Suppress("UNCHECKED_CAST")
            return CharacterDetailsViewModel(savedStateHandle, characterRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}