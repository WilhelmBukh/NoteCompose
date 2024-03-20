package com.example.note.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.note.MainViewModel
import com.example.note.R
import com.example.note.datastoreRepository.PreferenceManager
import com.example.note.datastoreRepository.preferencesValue
import kotlinx.coroutines.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HelloScreen(
    context: Context,
    navController: NavHostController = rememberNavController(),
    viewModel: MainViewModel = viewModel(LocalContext.current as ComponentActivity)
) {
    Scaffold(
        modifier = Modifier
    ) {
        var selected by remember{ mutableStateOf(false) }
        var selected2 by remember{ mutableStateOf(false) }
        val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        Log.e("Log", "Hello screen")
        Column {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    //.fillMaxSize()
                    .padding(16.dp)
                    .padding(top = 130.dp)
            ) {

                item {
                    HelloScreenScreens(
                        selected = selected,
                        imageURL = R.drawable.photo, onClick = {
                            if(selected2){
                                selected = true
                                selected2 = false
                            } else{
                                selected = !selected
                            }
                            viewModel.saveDataStore(
                                value = preferencesValue.firstScreen.name,
                                preferencesKey = PreferenceManager.PreferencesKey.choiceAboutScreen,
                                context = context
                            )
                        }
                    )
                }
                item {
                    HelloScreenScreens(
                        selected = selected2,
                        imageURL = R.drawable.photo, onClick = {
                            if(selected) {
                                selected = false
                                selected2 = true
                            } else {
                                selected2 = !selected2
                            }
                            viewModel.saveDataStore(
                                value = preferencesValue.secondScreen.name,
                                preferencesKey = PreferenceManager.PreferencesKey.choiceAboutScreen,
                                context = context
                            )
                        }
                    )
                }
            }
            Button(
                onClick = {
                    viewModel.saveDataStore(
                        value = preferencesValue.firstAppStart.name, preferencesKey =
                        PreferenceManager.PreferencesKey.crossingHelloToMain, context
                    )
                    viewModel.readDataStore(
                        context,
                        preferencesKey = PreferenceManager.PreferencesKey.crossingHelloToMain
                    )
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 50.dp)
                    .size(width = 120.dp, height = 40.dp)
            ) {
                Text(text = "Done")
            }
        }
    }
}

@Composable
fun HelloScreenScreens(
    imageURL: Int,
    onClick: () -> Unit = {},
    selected: Boolean = false,
    mainColor: Color = if (selected) MaterialTheme.colors.primary
    else MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
    width:Int = 0
) {
    Surface(
        modifier = Modifier.border(color = mainColor, width = 10.dp)
            .clickable {
                onClick()
            }
            .fillMaxSize()

    ) {
        Image(
            painter = painterResource(id = imageURL),
            contentDescription = null
        )
    }
}
