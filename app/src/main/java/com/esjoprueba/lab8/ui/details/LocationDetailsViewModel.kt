package com.esjoprueba.lab8.ui.details

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.esjoprueba.lab8.data.repository.LocationRepository
import com.esjoprueba.lab8.data.room.DatabaseProvider
import com.esjoprueba.lab8.ui.states.LocationDetailsUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class LocationDetailsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val locationRepository: LocationRepository
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

            // Generar número aleatorio del 1 al 10
            val randomNumber = Random.nextInt(1, 11)

            if (randomNumber % 2 == 0) {
                // Número par - mostrar data desde Room
                try {
                    val locationId = savedStateHandle.get<String>("locationId")?.toIntOrNull()
                    val location = if (locationId != null) {
                        locationRepository.getLocationById(locationId)
                    } else null

                    _uiState.value = LocationDetailsUiState(
                        isLoading = false,
                        data = location,
                        hasError = false
                    )
                } catch (_: Exception) {
                    _uiState.value = LocationDetailsUiState(
                        isLoading = false,
                        data = null,
                        hasError = true
                    )
                }
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

class LocationDetailsViewModelFactory(
    private val context: Context,
    private val savedStateHandle: SavedStateHandle
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationDetailsViewModel::class.java)) {
            val db = DatabaseProvider.getDatabase(context)
            val locationRepo = LocationRepository(db.locationDao())
            @Suppress("UNCHECKED_CAST")
            return LocationDetailsViewModel(savedStateHandle, locationRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}