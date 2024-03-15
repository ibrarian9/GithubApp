package com.app.githubApp.remote.response

import com.google.gson.annotations.SerializedName

data class GithubResponse(
	val totalCount: Int,
	val incompleteResults: Boolean,
	val items: List<UsersItem>
)

data class UsersItem(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,
)

