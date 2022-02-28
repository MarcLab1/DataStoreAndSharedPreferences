package com.datastoreandsharedpreferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DataStoreViewModel (
    app: Application,
) : AndroidViewModel(app) {

    private val sharedPreferences: SharedPreferences =
        app.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "datastore_prefs")
    private val dataStore = app.dataStore
    private var booldataStoreKey = booleanPreferencesKey(Constants.DARK_MODE_KEY)

    val darkThemeSharedPrefs: MutableState<Boolean> = mutableStateOf(false)
    val darkThemeDataStore: MutableState<Boolean> = mutableStateOf(false)
    val loadedValue = mutableStateOf("")

    val dogDataStoreKey = stringPreferencesKey("dog")
    val dogFlow: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[dogDataStoreKey] ?: "key dog has no value"
        }

    init {
        viewModelScope.launch {
            dataStore.edit {
                darkThemeDataStore.value = it[booldataStoreKey] ?: false
            }
        }
    }
    fun toggleThemeSharedPrefs() {
        editor.putBoolean(
            Constants.DARK_MODE_KEY,
            !sharedPreferences.getBoolean(Constants.DARK_MODE_KEY, false)
        )
        editor.apply()
        editor.commit()
        darkThemeSharedPrefs.value = sharedPreferences.getBoolean(Constants.DARK_MODE_KEY, true)
    }

    fun toggleThemeDataStore() {
        viewModelScope.launch {
            dataStore.edit {
                val bool = it[booldataStoreKey] ?: false
                darkThemeDataStore.value = !bool
                it[booldataStoreKey] = !bool
            }
        }
    }

    fun saveKeyValue(key: String, value: String) {
        val stringdataStoreKey = stringPreferencesKey(key)
        viewModelScope.launch {
            dataStore.edit {
                it[stringdataStoreKey] = value
            }
        }
    }

    fun loadKeyValue(key: String) {
        val stringdataStoreKey = stringPreferencesKey(key)
        viewModelScope.launch {
            val prefs = dataStore.data.first()
            loadedValue.value = prefs[stringdataStoreKey] ?: "key does not exist"
        }
    }
}