package com.example.note.ui.bottombar

import androidx.activity.ComponentActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

import com.example.note.ui.graphs.Route

sealed class MainBottomBar(
    var route: String,
    var icon: ImageVector,
) {
    object Home : MainBottomBar(
        route = Route.MainScreen.route,
       /* if (selectedScreen == preferencesValue.firstScreen.name)
            Route.MainScreen.route
        else
            Route.LessAboutScreen.route,*/
        icon = Icons.Default.Home
    )

    object Selected : MainBottomBar(
        route = Route.SelectedScreen.route,
        /*if (selectedScreen == preferencesValue.firstScreen.name)
            Route.SelectedScreen.route
        else
            Route.LessAboutSelectedScreen.route,*/
        icon = Icons.Default.Star
    )
}
