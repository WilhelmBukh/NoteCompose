package com.example.note.ui.screens

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.note.MainViewModel
import com.example.note.ui.graphs.BottomBar
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SelectedNoteScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel(LocalContext.current as ComponentActivity),
    onClick: () -> Unit = {},
    bottomBarVisibility: Boolean = true
) {
    viewModel.updateSelectedNote()
    val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    val context = LocalContext.current
    Scaffold() {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                    .weight(1f)
            ) {
                items(viewModel.saveFlow.value!!) { item ->
                    noteCard(modeldb = item, navController, onClick)
                }
            }
            if (bottomBarVisibility)
                BottomBar(navController = navController)
        }
    }

}