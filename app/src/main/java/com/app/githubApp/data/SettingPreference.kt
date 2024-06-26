package com.app.githubApp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")
class SettingPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getThemeSet(): Flow<Boolean> {
        return dataStore.data.map {
            it[THEME] ?: false
        }
    }

    suspend fun saveThemeSet(isDarkModeActive: Boolean) {
        dataStore.edit {
            it[THEME] = isDarkModeActive
        }
    }

    companion object {
        private val THEME = booleanPreferencesKey("SetTheme")

        @Volatile
        private var INSTANCE: SettingPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}