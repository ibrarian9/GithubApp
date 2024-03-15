package com.app.githubApp.ui.favoriteActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.githubApp.data.GitUserRepository
import com.app.githubApp.remote.database.GitUser

class FavoriteViewModel(private val repository: GitUserRepository) : ViewModel() {
    fun getFavoriteUsers(): LiveData<List<GitUser>> {
        return repository.getAllFavorite()
    }
}