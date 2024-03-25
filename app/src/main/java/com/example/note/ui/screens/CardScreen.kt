package com.example.note.ui.screens

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.note.MainViewModel
import com.example.note.db.Modeldb

//карточка(сама заметка) на главном экране и экране избранного
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun noteCard(
    modeldb: Modeldb,
    navController: NavHostController = rememberNavController(),
    onClick: () -> Unit = {},
    mainViewModel: MainViewModel = viewModel(LocalContext.current as ComponentActivity),
    scaffoldStateUpdate: (scaffoldState: BackdropScaffoldState) -> Unit = {},
    cardBooleanState: Boolean = mainViewModel.card_size_state.value //something like "shall we make card bigger or not?"
) {
    var header_length = animateIntAsState(targetValue = if (cardBooleanState) 80 else 200)
    var buttons_length = animateIntAsState(targetValue = if (cardBooleanState) 40 else 100)
    var selected by remember { mutableStateOf(false) }

    Log.e("log", selected.toString())
    val selectedColor by animateColorAsState(if (selected) MaterialTheme.colors.error else MaterialTheme.colors.background)

    Surface(
        modifier = Modifier
            .height(100.dp)
            .clickable {
                if (selected) {
                    mainViewModel.setModeldb(Modeldb())
                    onClick()
                    scaffoldStateUpdate(BackdropScaffoldState(BackdropValue.Concealed))
                    selected = false
                } else {
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
        Column {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            ) {
                Text(
                    text = modeldb.title,
                    Modifier
                        .background(MaterialTheme.colors.error)
                        .width(header_length.value.dp).height(30.dp),
                    maxLines = 1
                )

                Button(
                    onClick = {
                        mainViewModel.setModeldb(modeldb)
                        onClick()
                        scaffoldStateUpdate(BackdropScaffoldState(BackdropValue.Concealed))
                    },
                    Modifier
                        .background(MaterialTheme.colors.error)
                        .width(buttons_length.value.dp).height(30.dp)
                ) {
                    Icon(Icons.Filled.Create, null,
                        modifier = Modifier.height(50.dp).width(50.dp))
                }
                Button(
                    onClick = {
                        mainViewModel.deleteNote(modeldb)
                    },
                    Modifier
                        .background(MaterialTheme.colors.error)
                        .width(buttons_length.value.dp).height(30.dp)
                ) {
                    Icon(
                        Icons.Filled.Delete,
                        null,
                        modifier = Modifier.height(50.dp).width(50.dp))
                }

            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = modeldb.text,
                modifier = Modifier
                    .padding(5.dp),
                color = MaterialTheme.colors.onSecondary,
            )
        }
    }
}