package com.example.note.ui.graphs

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.note.MainScreenViewModel
import com.example.note.ui.bottombar.MainBottomBar
import com.example.note.ui.theme.Purple500
import com.example.note.ui.theme.Purple700

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottomNavGraph(
    navController: NavHostController = rememberNavController(),
    viewModel: MainScreenViewModel = viewModel(
        LocalContext.current as ComponentActivity
    )
) {
    var saveState by remember{ mutableStateOf(Unit) }
    var tabPage by remember { mutableStateOf(TabPage.HOME) }
    val backgroundColor by animateColorAsState(if (tabPage == TabPage.HOME) Purple700 else Purple500)
    Scaffold(
        bottomBar = {
            HomeTabBar(
                differentState = viewModel.startDestination.value,
                navController = navController,
                backgroundColor = backgroundColor,
                tabPage = tabPage,
                onTabSelected = { tabPage = it },
                toScreen = {
                    if (tabPage == TabPage.HOME) {

                            navController.navigate(Route.FullAboutScreen.route)

                    } else {
                            navController.navigate(Route.SelectedScreen.route)

                    }
                })
        }//BottomBar(navController,viewModel) },
    ) {
        MainNavGraph(navController = navController, LessState = {saveState = it})
    }
}

@Composable
fun BottomBar(
    navController: NavHostController, viewModel: MainScreenViewModel = viewModel(
        LocalContext.current as ComponentActivity
    )
) {
    var homeScreen = MainBottomBar.Home
    homeScreen.route =
            Route.MainScreen.route


    var selectedScreen = MainBottomBar.Selected
    selectedScreen.route =
            Route.SelectedScreen.route
    Log.e("Log", "in Bottom Bar ${viewModel.startDestination.value}")
    val screens = listOf(
        homeScreen,
        selectedScreen
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: MainBottomBar,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        })
}