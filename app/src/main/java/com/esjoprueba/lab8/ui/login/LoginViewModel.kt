package com.esjoprueba.lab8.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.esjoprueba.lab8.data.repository.CharacterRepository
import com.esjoprueba.lab8.data.repository.LocationRepository
import com.esjoprueba.lab8.data.repository.UserPreferencesRepository
import com.esjoprueba.lab8.data.room.DatabaseProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val isLoading: Boolean = false
)

class LoginViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val characterRepository: CharacterRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(name: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = LoginUiState(isLoading = true)

            // Delay de 4 segundos
            delay(4000)

            // Sincronizar datos de personajes y ubicaciones
            try {
                characterRepository.syncCharacters()
                locationRepository.syncLocations()

                // Guardar el nombre en DataStore
                userPreferencesRepository.saveUserName(name)

                _uiState.value = LoginUiState(isLoading = false)
                onSuccess()
            } catch (e: Exception) {
                _uiState.value = LoginUiState(isLoading = false)
                // Aquí podrías manejar el error
            }
        }
    }
}

class LoginViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            val db = DatabaseProvider.getDatabase(context)
            val userPreferencesRepo = UserPreferencesRepository.getInstance(context)
            val characterRepo = CharacterRepository(db.characterDao())
            val locationRepo = LocationRepository(db.locationDao())

            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(userPreferencesRepo, characterRepo, locationRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}