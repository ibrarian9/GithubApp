package com.app.githubApp.ui.detailActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.githubApp.data.GitUserRepository
import com.app.githubApp.remote.database.GitUser
import com.app.githubApp.remote.response.FollResponseItem
import com.app.githubApp.remote.response.ProfileResponse

class DetailViewModel(private val repository: GitUserRepository): ViewModel() {
    fun getDetailUser(username: String): LiveData<ProfileResponse> {
        return repository.getDetailUser(username)
    }
    fun getFollower(username: String): LiveData<List<FollResponseItem>> {
        return repository.getFollower(username)
    }
    fun getFollowing(username: String): LiveData<List<FollResponseItem>> {
        return repository.getFollowing(username)
    }
    fun getFavUserByUsername(username: String): LiveData<GitUser> {
        return repository.getFavUserByUsername(username)
    }
    fun deleteUsers(gitUser: GitUser) {
        return repository.deleteUsers(gitUser)
    }
    fun insertUsers(gitUser: GitUser) {
        return repository.insertUsers(gitUser)
    }
    fun getLoading(): LiveData<Boolean> {
        return repository.isLoading
    }
}