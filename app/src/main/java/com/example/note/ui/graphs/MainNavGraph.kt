package com.example.note.ui.graphs

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.material.BackdropScaffoldState
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.note.MainScreenViewModel
import com.example.note.ui.screens.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainNavGraph(navController: NavHostController,LessState:(unit:Unit)->Unit) {
    var saveState by remember{ mutableStateOf(Unit) }
    LessState(saveState)
    val viewModel: MainScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        LocalContext.current as ComponentActivity
    )
    Log.e("log",viewModel.startDestination.value)
    var backdropScaffoldState by remember{ mutableStateOf(BackdropScaffoldState(BackdropValue.Revealed)) }
    NavHost(
        navController = navController,
        route = Route.Root.route,
        startDestination = Route.MainScreen.route
    ) {
        composable(route = Route.MainScreen.route) {
            MainScreen(
                navController,
                onClick = {
                    Log.e("log", "Destination is " + viewModel.startDestination.value)
                    navController.navigate(Route.FullAboutScreen.route)
                    /*if (viewModel.startDestination.value == Route.FullAboutScreen.route) {
                            navController.navigate(Route.FullAboutScreen.route)
                    } else
                        {
                            //navController.navigate(Route.LessAboutScreen.route)
                        }*/

                }
            )
        }

        composable(route = Route.SelectedScreen.route) {
            SelectedNoteScreen(navController,
                onClick = {
                    //if(viewModel.startDestination.value == Route.FullAboutScreen.route)
                    navController.navigate(Route.FullAboutScreen.route)
                })
        }

        composable(route = Route.FullAboutScreen.route) {
            FullAboutScreen(navController = navController, onClick =
            {navController.popBackStack(Route.FullAboutScreen.route, inclusive = true)}
                /*if (viewModel.startDestination.value == Route.FullAboutScreen.route) {
                    {
                        navController.popBackStack(Route.FullAboutScreen.route, inclusive = true)
                    }
                } else {
                    {
                        //navController.popBackStack(Route.LessAboutScreen.route, inclusive = true)
                        //navController.navigate(Route.LessAboutScreen.route)
                    }
                //navController.popBackStack(Route.FullAboutScreen.route, inclusive = true)
            }*/)
        }
    }
}

sealed class Route(val route: String) {
    object Root : Route("ROOT")
    object MainScreen : Route("HOME")
    object SelectedScreen : Route("SELECTED")
    object FullAboutScreen : Route("ABOUT")
}