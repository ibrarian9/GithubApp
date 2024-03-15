package com.app.githubApp.remote.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GitUser::class], version = 1, exportSchema = false)
abstract class GitUsersDatabase : RoomDatabase() {
    abstract fun gitUserDao(): GitUserDao

    companion object {
        @Volatile
        private var INSTANCE: GitUsersDatabase? = null
        fun getInstance(context: Context): GitUsersDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    GitUsersDatabase::class.java, "git_database"
                ).build()
            }
    }

}