package com.example.note.datastoreRepository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preference")

class PreferenceManager(context: Context) {
    val coroutineScope = CoroutineScope(SupervisorJob()+Dispatchers.Default)

    object PreferencesKey {
        val onBoardingKey = booleanPreferencesKey(name = "on_boarding_completed")
        val choiceAboutScreen = stringPreferencesKey(name = "second")
        val crossingHelloToMain = stringPreferencesKey(name = "crossingHelloToMain")
        val test = stringPreferencesKey(name = "nameOfTest")
    }

    private val dataStore = context.dataStore

    suspend fun saveOnDataStore(value: String, preferencesKey: Preferences.Key<String>) {
            dataStore.edit { preferences ->
                preferences[preferencesKey] = value
        }
    }

    suspend fun readWithDataStore(preferencesKey: Preferences.Key<String>,defaultValue:String = ""): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onBoardingState = preferences[preferencesKey]?:defaultValue
                onBoardingState
            }
    }

    fun readWithDataStore2(preferencesKey: Preferences.Key<String>): Flow<String> {
        return dataStore.data
            .map { preferences ->
                val onBoardingState = preferences[preferencesKey]?:""
                onBoardingState
            }
    }

    suspend fun saveOnBoardingState(value: String) {
        dataStore.edit { preferences ->
            //preferences[PreferencesKey.onBoardingKey] = completed
            preferences[PreferencesKey.crossingHelloToMain] = value
        }
    }

    fun readOnBoardingState(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onBoardingState = preferences[PreferencesKey.test]?:""
                onBoardingState
            }
    }

    fun readOnBoardingState2(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onBoardingState = preferences[PreferencesKey.crossingHelloToMain]!!
                onBoardingState
            }
    }
}

enum class preferencesValue {
    firstScreen, secondScreen, thirdScreen,
    firstAppStart
}
