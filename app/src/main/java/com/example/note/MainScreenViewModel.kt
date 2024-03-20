package com.example.note

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.datastoreRepository.PreferenceManager
import com.example.note.datastoreRepository.preferencesValue
import com.example.note.ui.graphs.Route
import kotlinx.coroutines.launch

class MainScreenViewModel(application: Application): AndroidViewModel(application){
    private var _isLoading: MutableState<String> = mutableStateOf("")
    var isLoading = _isLoading
    private val _startDestination: MutableState<String> = mutableStateOf(Route.FullAboutScreen.route)
    val startDestination = _startDestination
    init {
        viewModelScope.launch{
            PreferenceManager(application.applicationContext).readWithDataStore(
                preferencesKey = PreferenceManager.PreferencesKey.choiceAboutScreen
            ).collect{item->
                    _startDestination.value = Route.FullAboutScreen.route

            }
        }
        _isLoading.value = preferencesValue.secondScreen.name
    }
}