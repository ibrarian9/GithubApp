package com.app.githubApp.remote.retrofit

import com.app.githubApp.remote.response.FollResponseItem
import com.app.githubApp.remote.response.GithubResponse
import com.app.githubApp.remote.response.ProfileResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @Headers("Authorization: token $TOKEN")
    @GET("search/users")
    fun getUsers(
        @Query("q") username: String
    ): Call<GithubResponse>

    @Headers("Authorization: token $TOKEN")
    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<ProfileResponse>

    @Headers("Authorization: token $TOKEN")
    @GET("users/{username}/followers")
    fun getFollowerUser(
        @Path("username") username: String
    ): Call<List<FollResponseItem>>

    @Headers("Authorization: token $TOKEN")
    @GET("users/{username}/following")
    fun getFollowingUser(
        @Path("username") username: String
    ): Call<List<FollResponseItem>>

    companion object {
        const val TOKEN = "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"
    }
}