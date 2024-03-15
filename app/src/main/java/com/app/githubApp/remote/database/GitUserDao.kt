package com.app.githubApp.remote.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface GitUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUsers(gitUser: GitUser)

    @Delete
    fun deleteUsers(gitUser: GitUser)

    @Query("SELECT * FROM gituser")
    fun getAllFavorite(): LiveData<List<GitUser>>

    @Query("SELECT * FROM gituser WHERE username = :username")
    fun getFavUserByUsername(username: String): LiveData<GitUser>
}