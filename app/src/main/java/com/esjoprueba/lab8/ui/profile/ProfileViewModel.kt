package com.esjoprueba.lab8.ui.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.esjoprueba.lab8.data.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val userName: Flow<String?> = userPreferencesRepository.userName

    fun logout(onLogoutSuccess: () -> Unit) {
        viewModelScope.launch {
            userPreferencesRepository.clearUserName()
            onLogoutSuccess()
        }
    }
}

class ProfileViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            val userPreferencesRepo = UserPreferencesRepository.getInstance(context)
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(userPreferencesRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}