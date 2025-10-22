package com.esjoprueba.lab8.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.esjoprueba.lab8.data.repository.UserPreferencesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val isLoading: Boolean = false
)

class LoginViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(name: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = LoginUiState(isLoading = true)

            // Delay de 2 segundos para simular proceso de login
            delay(2000)

            try {
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
            val userPreferencesRepo = UserPreferencesRepository.getInstance(context)

            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(userPreferencesRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}