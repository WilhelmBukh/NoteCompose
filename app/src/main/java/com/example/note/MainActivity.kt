package com.example.note

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.note.datastoreRepository.PreferenceManager
import com.example.note.datastoreRepository.preferencesValue
import com.example.note.ui.graphs.BottomNavGraph
import com.example.note.ui.theme.NoteTheme
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

class MainActivity() : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent() {
            NoteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
                    val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
                    viewModel.initDatabase(applicationContext)
                    //RootNavGraph(context = applicationContext)
                    BottomNavGraph()
                }
            }
        }
    }
}