package com.example.note_todo_app.data.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsDataStore(private val context: Context) {
    
    object PreferencesKeys {
        val AUTO_SYNC_ENABLED = booleanPreferencesKey("auto_sync_enabled")
        val DARK_THEME_ENABLED = booleanPreferencesKey("dark_theme_enabled")
        val DEFAULT_NOTE_SORT_ORDER = stringPreferencesKey("default_note_sort_order")
        val INK_TOOL_COLOR = stringPreferencesKey("ink_tool_color")
        val INK_TOOL_SIZE = stringPreferencesKey("ink_tool_size")
        val LAST_SYNC_TIME = stringPreferencesKey("last_sync_time")
    }

    // Auto sync setting
    val autoSyncEnabledFlow = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.AUTO_SYNC_ENABLED] ?: true
        }
    
    suspend fun setAutoSyncEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.AUTO_SYNC_ENABLED] = enabled
        }
    }

    // Dark theme setting
    val darkThemeEnabledFlow = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.DARK_THEME_ENABLED] ?: false
        }
    
    suspend fun setDarkThemeEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.DARK_THEME_ENABLED] = enabled
        }
    }

    // Default note sort order
    val defaultNoteSortOrderFlow = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.DEFAULT_NOTE_SORT_ORDER] ?: "date_desc"
        }
    
    suspend fun setDefaultNoteSortOrder(order: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.DEFAULT_NOTE_SORT_ORDER] = order
        }
    }

    // Ink tool color
    val inkToolColorFlow = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.INK_TOOL_COLOR] ?: "#000000"
        }
    
    suspend fun setInkToolColor(color: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.INK_TOOL_COLOR] = color
        }
    }

    // Ink tool size
    val inkToolSizeFlow = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.INK_TOOL_SIZE] ?: "5.0"
        }
    
    suspend fun setInkToolSize(size: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.INK_TOOL_SIZE] = size
        }
    }

    // Last sync time
    val lastSyncTimeFlow = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.LAST_SYNC_TIME] ?: ""
        }
    
    suspend fun setLastSyncTime(time: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.LAST_SYNC_TIME] = time
        }
    }
}