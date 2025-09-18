package com.esjoprueba.lab8.ui.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esjoprueba.lab8.data.LocationDb
import com.esjoprueba.lab8.ui.states.LocationsUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class LocationsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LocationsUiState())
    val uiState: StateFlow<LocationsUiState> = _uiState.asStateFlow()

    init {
        loadLocations()
    }

    fun loadLocations() {
        viewModelScope.launch {
            _uiState.value = LocationsUiState(isLoading = true)

            // Simular loading de 4 segundos
            delay(4000)

            // Generar número aleatorio del 1 al 10
            val randomNumber = Random.nextInt(1, 11)

            if (randomNumber % 2 == 0) {
                // Número par - mostrar data
                val locations = LocationDb.getLocations()
                _uiState.value = LocationsUiState(
                    isLoading = false,
                    data = locations,
                    hasError = false
                )
            } else {
                // Número impar - mostrar error
                _uiState.value = LocationsUiState(
                    isLoading = false,
                    data = emptyList(),
                    hasError = true
                )
            }
        }
    }

    fun retry() {
        loadLocations()
    }
}