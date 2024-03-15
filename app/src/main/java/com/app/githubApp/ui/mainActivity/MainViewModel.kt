package com.app.githubApp.ui.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.githubApp.data.GitUserRepository
import com.app.githubApp.remote.database.GitUser
import com.app.githubApp.remote.response.UsersItem

class MainViewModel(private val repository: GitUserRepository) : ViewModel() {
    fun getAllUsers(query: String): LiveData<List<UsersItem>> {
        return repository.getListUsers(query)
    }

    fun getLoading(): LiveData<Boolean> {
        return repository.isLoading
    }
}