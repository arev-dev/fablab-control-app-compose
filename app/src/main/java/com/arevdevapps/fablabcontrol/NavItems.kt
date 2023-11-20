package com.arevdevapps.fablabcontrol

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val listOfNavItems : List<NavItem> = listOf(
    NavItem(
        label = "Controles",
        icon = Icons.Default.Settings,
        route = Screens.ControllsScreen.name
    ),
    NavItem(
        label = "Inicio",
        icon = Icons.Default.Home,
        route = Screens.HomeScreen.name
    ),
    NavItem(
        label = "Dispositivos",
        icon = Icons.Default.AddCircle,
        route = Screens.DevicesScreen.name
    )
)