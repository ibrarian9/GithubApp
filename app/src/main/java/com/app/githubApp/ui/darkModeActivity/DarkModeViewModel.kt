package com.app.githubApp.ui.darkModeActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.app.githubApp.data.GitUserRepository

class DarkModeViewModel(private val repository: GitUserRepository): ViewModel() {
    fun getThemeSet(): LiveData<Boolean> {
        return repository.getThemeSetting()
    }

    suspend fun saveThemeSet(isDarkModeActive: Boolean) {
        return repository.saveThemeSet(isDarkModeActive)
    }
}