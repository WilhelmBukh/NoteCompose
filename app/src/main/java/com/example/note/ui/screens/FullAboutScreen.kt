package com.example.note.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.note.MainViewModel
import com.example.note.db.Modeldb
import com.example.note.doRoom
import com.example.note.ui.graphs.Route
import com.example.note.ui.topAppBar.AboutTopAppBar

//показывает информацию из земетки(открывается на пол экрана и пользователь может открыть на весь(свайп вверх)
@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FullAboutScreen(
    onClick: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
    mainViewModel: MainViewModel = viewModel(LocalContext.current as ComponentActivity),
    updateBackground: () -> Unit = {},
    updateBackgroundBoolean: Boolean = false,
    backStackFun: (backStack: Boolean) -> Unit = {},
    closeForeground: (value: BackdropScaffoldState) -> Unit = {},
    foregroundState:BackdropScaffoldState = BackdropScaffoldState(BackdropValue.Revealed)
) {
    var backStack by remember { mutableStateOf(false) }
    var modeldb = mainViewModel.getModeldb()
    var text by remember { mutableStateOf(modeldb.text) }
    var title by remember { mutableStateOf(modeldb.title) }
    val changeText by remember { mutableStateOf(modeldb.text) }
    val changeTitle by remember { mutableStateOf(modeldb.title) }
    val id by remember { mutableStateOf(modeldb.id) }
    var checkState by remember { mutableStateOf(false) }
    var openTitle by remember { mutableStateOf(false) }
    var openText by remember { mutableStateOf(false) }
    Scaffold(topBar = {
        AboutTopAppBar(
            navController,
            onClick,
            modeldb = Modeldb(id = id, title = title, text = text, selected = modeldb.selected),
            checkState = checkState,
            changeStateFun = { checkState = false },
            roomDoing = if (changeTitle == "" && changeText == "") {
                doRoom.insert
            } else {
                doRoom.update
            },
            titleValue = title,
            textValue = text,
            updateBackground = updateBackground,
            updateBackgroundBoolean = updateBackgroundBoolean,
            backStackFun = { back ->
                backStack = back
            },
            changeModeldb = {modeldb = it},
            closeForeground = { closeForeground(it) },
            navigateToFullScreen = {navController.navigate(Route.FullAboutScreen.route)},
            visibleDeleteButton =
            if(text!=Modeldb().text||title!=Modeldb().title) true else false
        )
    }) {
        if (backStack) Log.e("Log", "first") else Log.e("Log", "second")
        Column {
            OutlinedTextField(
                modifier = Modifier.width(500.dp),
                value = title,
                onValueChange = {
                    title = it
                    checkState = if(it != changeTitle) true else false
                    if (it == "") openTitle = true
                }
            )
            Spacer(modifier = Modifier.padding(16.dp))
            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                    checkState = if(it != changeText) true else false
                    if (it == "") openText = true
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

