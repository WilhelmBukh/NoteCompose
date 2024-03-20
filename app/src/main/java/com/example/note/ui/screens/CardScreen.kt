package com.example.note.ui.screens

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.note.MainViewModel
import com.example.note.db.Modeldb
import com.example.note.ui.graphs.Route
import com.example.note.ui.theme.NoteTheme

//карточка(сама заметка) на главном экране и экране избранного
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun noteCard(
    modeldb: Modeldb,
    navController: NavHostController = rememberNavController(),
    onClick: () -> Unit = {},
    mainViewModel: MainViewModel = viewModel(LocalContext.current as ComponentActivity),
    scaffoldStateUpdate: (scaffoldState: BackdropScaffoldState) -> Unit = {}
) {
    var selected by remember { mutableStateOf(false) }
    Log.e("log",selected.toString())
    val selectedColor by animateColorAsState(if(selected) MaterialTheme.colors.error else MaterialTheme.colors.background)
    Surface(
        modifier = Modifier
            .height(100.dp)
            .clickable {
                if (selected) {
                    mainViewModel.setModeldb(Modeldb())
                    onClick()
                    scaffoldStateUpdate(BackdropScaffoldState(BackdropValue.Concealed))
                    selected = false
                }
                else {
                    mainViewModel.setModeldb(modeldb)
                    onClick()
                    scaffoldStateUpdate(BackdropScaffoldState(BackdropValue.Concealed))
                    selected = true
                }
            }
            .border(color = selectedColor, width = 4.dp),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colors.primary,
    ) {
        Text(
            text = modeldb.text,
            modifier = Modifier
                .padding(5.dp)
                .background(color = Color.White.copy(alpha = 0.8f)),
            color = MaterialTheme.colors.onSecondary,
        )
    }
}