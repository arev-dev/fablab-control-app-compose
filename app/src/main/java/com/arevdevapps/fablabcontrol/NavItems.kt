package com.arevdevapps.fablabcontrol

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val listOfNavItems : List<NavItem> = listOf(
    NavItem(
        label = "Controles",
        icon = Icons.Rounded.Settings,
        route = Screens.ControllsScreen.name
    ),
    NavItem(
        label = "Inicio",
        icon = Icons.Rounded.Home,
        route = Screens.HomeScreen.name
    ),
    NavItem(
        label = "Dispositivos",
        icon = Icons.Rounded.AddCircle,
        route = Screens.DevicesScreen.name
    )
)