package com.esjoprueba.lab8.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esjoprueba.lab8.data.LocationDb
import com.esjoprueba.lab8.ui.states.LocationDetailsUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class LocationDetailsViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(LocationDetailsUiState())
    val uiState: StateFlow<LocationDetailsUiState> = _uiState.asStateFlow()

    init {
        loadLocationDetails()
    }

    private fun loadLocationDetails() {
        viewModelScope.launch {
            _uiState.value = LocationDetailsUiState(isLoading = true)

            // Simular loading de 2 segundos
            delay(2000)

            // Obtener ID del SavedStateHandle
            val locationId = savedStateHandle.get<String>("locationId")?.toIntOrNull()

            // Generar número aleatorio del 1 al 10
            val randomNumber = Random.nextInt(1, 11)

            if (randomNumber % 2 == 0) {
                // Número par - mostrar data
                val location = if (locationId != null) {
                    LocationDb.getLocationById(locationId)
                } else null

                _uiState.value = LocationDetailsUiState(
                    isLoading = false,
                    data = location,
                    hasError = false
                )
            } else {
                // Número impar - mostrar error
                _uiState.value = LocationDetailsUiState(
                    isLoading = false,
                    data = null,
                    hasError = true
                )
            }
        }
    }

    fun retry() {
        loadLocationDetails()
    }
}