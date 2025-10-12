package com.esjoprueba.lab8.ui.locations

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.esjoprueba.lab8.data.repository.LocationRepository
import com.esjoprueba.lab8.data.room.DatabaseProvider
import com.esjoprueba.lab8.ui.states.LocationsUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class LocationsViewModel(
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LocationsUiState())
    val uiState: StateFlow<LocationsUiState> = _uiState.asStateFlow()

    init {
        loadLocations()
    }

    private fun loadLocations() {
        viewModelScope.launch {
            _uiState.value = LocationsUiState(isLoading = true)

            // Simular loading de 4 segundos
            delay(4000)

            // Generar número aleatorio del 1 al 10
            val randomNumber = Random.nextInt(1, 11)

            if (randomNumber % 2 == 0) {
                // Número par - mostrar data desde Room
                try {
                    val locations = locationRepository.getAllLocations()
                    _uiState.value = LocationsUiState(
                        isLoading = false,
                        data = locations,
                        hasError = false
                    )
                } catch (_: Exception) {
                    _uiState.value = LocationsUiState(
                        isLoading = false,
                        data = emptyList(),
                        hasError = true
                    )
                }
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

class LocationsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationsViewModel::class.java)) {
            val db = DatabaseProvider.getDatabase(context)
            val locationRepo = LocationRepository(db.locationDao())
            @Suppress("UNCHECKED_CAST")
            return LocationsViewModel(locationRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
