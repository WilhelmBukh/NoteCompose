package com.example.note

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.datastoreRepository.PreferenceManager
import com.example.note.db.Database
import com.example.note.db.Modeldb
import com.example.note.db.RepositoryRealization
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MainViewModel() : ViewModel() {
    private var _saveDataStore :MutableStateFlow<String> = MutableStateFlow("")
    var saveDataStore:StateFlow<String> = _saveDataStore
    private var _saveFlow:MutableStateFlow<List<Modeldb>?> = MutableStateFlow(null)
    var saveFlow:StateFlow<List<Modeldb>?> = _saveFlow
    private var modeldbBuffer = mutableStateOf(Modeldb())
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    fun initDatabase(context: Context) {
        globalDao = Database.getInstance(context).getDao()
    }

    fun clearModeldb() {
        modeldbBuffer.value = Modeldb(title = "", text = "")
    }

    fun setModeldb(modeldb: Modeldb) {
        modeldbBuffer.value = modeldb
    }

    fun getModeldb(): Modeldb {//TODO Changed
        return modeldbBuffer.value
    }

    fun insertNote(modeldb: Modeldb) {
        coroutineScope.launch {
            globalDao.insert(modeldb)
        }
    }

    fun updateNote(modeldb: Modeldb) {
        coroutineScope.launch {
            globalDao.update(modeldb)
        }
    }

    fun updateSelectedNote() {
        viewModelScope.launch(Dispatchers.IO) {
            RepositoryRealization(globalDao).allSelectedFlow.collect {
                _saveFlow.value = it
            }
        }
    }

    fun deleteNote(modeldb: Modeldb){
        viewModelScope.launch {
            globalDao.delete(modeldb)
        }
    }

    fun coutToast(context: Context, value: String) {
        Toast.makeText(context, value, Toast.LENGTH_SHORT).show()
    }

    fun readDataStore(context: Context, preferencesKey: Preferences.Key<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            PreferenceManager(context).readWithDataStore(preferencesKey).collect {
                _saveDataStore.value = it
                //saveDataStore = _saveDataStore
            }
        }

    }

    fun saveDataStore(value: String, preferencesKey: Preferences.Key<String>, context: Context) {
        viewModelScope.launch {
            PreferenceManager(context = context).saveOnDataStore(
                value = value,
                preferencesKey = preferencesKey
            )
        }
    }
}