package com.esgi.todoapp.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class ThemePreferences @Inject constructor(
    private val context: Context
) {
    private val dataStore = context.dataStore
    private val isDarkModeKey = booleanPreferencesKey("is_dark_mode")

    val isDarkMode: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[isDarkModeKey] ?: false
    }

    suspend fun toggleTheme() {
        dataStore.edit { preferences ->
            val current = preferences[isDarkModeKey] ?: false
            preferences[isDarkModeKey] = !current
        }
    }
}