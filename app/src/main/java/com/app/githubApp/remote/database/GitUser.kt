package com.app.githubApp.remote.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class GitUser (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    var name: String = "",

    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String? = null,

): Parcelable