package com.app.githubApp.data

import android.content.Context
import com.app.githubApp.remote.database.GitUsersDatabase
import com.app.githubApp.remote.retrofit.BaseApi

object Injection {
    fun provideRepo(context: Context): GitUserRepository {
        val apiService = BaseApi.getApiService()
        val db = GitUsersDatabase.getInstance(context)
        val dao = db.gitUserDao()
        return GitUserRepository.getInstance(apiService, dao)
    }
}