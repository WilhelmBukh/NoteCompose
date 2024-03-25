package com.example.note.ui.screens


import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.note.MainViewModel
import com.example.note.globalDao
import com.example.note.ui.graphs.HomeTabBar
import com.example.note.ui.graphs.Route
import com.example.note.ui.graphs.TabPage
import com.example.note.ui.theme.Purple500
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

enum class whereScreenForBottonBar {
    mainScreen, selectedScreen
}

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: MainViewModel = viewModel(LocalContext.current as ComponentActivity),
    onClick: () -> Unit = {},
    bottomBarVisibility: Boolean = true,
    floatingButtonVisibility: Boolean = true,
    scaffoldStateUpdate: (scaffoldState: BackdropScaffoldState) -> Unit = {},
    whereScreen: whereScreenForBottonBar = whereScreenForBottonBar.mainScreen
) {
    val context = LocalContext.current
    val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    Scaffold(
        bottomBar = {
            if (bottomBarVisibility) {
                HomeTabBar(
                    backgroundColor = Purple500,
                    tabPage = TabPage.HOME,
                    onTabSelected = {},
                    toScreen = {},
                    differentState = ""
                )
            } else {
            }
        },
        floatingActionButton =
        if (floatingButtonVisibility) {
            {
                FloatingActionButton(onClick = {
                    viewModel.clearModeldb()
                    onClick()
                }) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }
        } else {
            {}
        }
    ) {
        var text by remember { mutableStateOf("") }
        val list by globalDao.getAllModelsdb().observeAsState(listOf())
        val list2 by globalDao.getAllSelected().observeAsState(listOf())
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Log.e("localError", "Recomposing")

            var columStateBoolean by remember { mutableStateOf(true) }
            var grid_amount = animateIntAsState(targetValue = if (columStateBoolean) 2 else 1)
            var next_five = animateIntAsState(targetValue = if (columStateBoolean) 20 else 0)

            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = {
                    columStateBoolean = !columStateBoolean
                    viewModel.card_size_state.value = columStateBoolean
                }) {
                    Icon(
                        if(columStateBoolean) Icons.Filled.MoreVert
                        else Icons.Filled.List,
                        ""
                    )
                }
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(grid_amount.value),
                verticalArrangement = Arrangement.spacedBy(next_five.value.dp),
                horizontalArrangement = Arrangement.spacedBy(next_five.value.dp),
                modifier = Modifier
                    .padding(
                        start = next_five.value.dp,
                        top = next_five.value.dp,
                        end = next_five.value.dp
                    )
                    .weight(1f)
            ) {
                items(
                    if (whereScreen == whereScreenForBottonBar.mainScreen) list.reversed()
                    else list2.reversed()
                ) { item ->
                    noteCard(
                        modeldb = item,
                        navController,
                        onClick,
                        scaffoldStateUpdate = { state ->
                            scaffoldStateUpdate(state)//TODO Remove that recomposition
                        }
                    )
                }
            }
            /*if (bottomBarVisibility)
                BottomBar(navController = navController)*/
        }
    }
}