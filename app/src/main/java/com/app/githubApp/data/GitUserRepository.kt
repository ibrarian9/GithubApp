package com.app.githubApp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.githubApp.remote.database.GitUser
import com.app.githubApp.remote.database.GitUserDao
import com.app.githubApp.remote.response.FollResponseItem
import com.app.githubApp.remote.response.GithubResponse
import com.app.githubApp.remote.response.ProfileResponse
import com.app.githubApp.remote.response.UsersItem
import com.app.githubApp.remote.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class GitUserRepository private constructor(
    private val apiService: ApiService,
    private val gitUserDao: GitUserDao
) {

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllFavorite(): LiveData<List<GitUser>> = gitUserDao.getAllFavorite()

    fun getFavUserByUsername(username: String): LiveData<GitUser> =
        gitUserDao.getFavUserByUsername(username)
    fun insertUsers(gitUser: GitUser) = executorService.execute {
        gitUserDao.insertUsers(gitUser)
    }
    fun deleteUsers(gitUser: GitUser) = executorService.execute {
        gitUserDao.deleteUsers(gitUser)
    }

    fun getListUsers(query: String): LiveData<List<UsersItem>> {
        _isLoading.value = true

        val listUsers = MutableLiveData<List<UsersItem>>()
        val user = apiService.getUsers(query)

        user.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    listUsers.value = response.body()!!.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                t.message
            }
        })
        return listUsers
    }

    fun getDetailUser(username: String): LiveData<ProfileResponse> {
        _isLoading.value = true

        val detailUser = MutableLiveData<ProfileResponse>()
        val user = apiService.getDetailUser(username)

        user.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    detailUser.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                _isLoading.value = false
                t.message
            }
        })
        return detailUser
    }

    fun getFollower(username: String): LiveData<List<FollResponseItem>> {
        _isLoading.value = true

        val followers = MutableLiveData<List<FollResponseItem>>()
        val user = apiService.getFollowerUser(username)

        user.enqueue(object : Callback<List<FollResponseItem>> {
            override fun onResponse(
                call: Call<List<FollResponseItem>>,
                response: Response<List<FollResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    followers.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<FollResponseItem>>, t: Throwable) {
                _isLoading.value = false
            }
        })
        return followers
    }

    fun getFollowing(username: String): LiveData<List<FollResponseItem>> {
        _isLoading.value = true

        val following = MutableLiveData<List<FollResponseItem>>()
        val user = apiService.getFollowingUser(username)

        user.enqueue(object : Callback<List<FollResponseItem>> {
            override fun onResponse(
                call: Call<List<FollResponseItem>>,
                response: Response<List<FollResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    following.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<FollResponseItem>>, t: Throwable) {
                _isLoading.value = false
            }
        })
        return following
    }
    companion object {
        private const val TAG = "MainViewModel"

        @Volatile
        var INSTANCE: GitUserRepository? = null
        fun getInstance(
            apiService: ApiService,
            gitUserDao: GitUserDao
        ): GitUserRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: GitUserRepository(apiService, gitUserDao)
            }.also { INSTANCE = it }
    }
}