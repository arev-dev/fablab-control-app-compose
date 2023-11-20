package com.arevdevapps.fablabcontrol

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.arevdevapps.fablabcontrol.Connectivity.BluetoothController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(bluetoothController: BluetoothController) {
    val navController: NavHostController = rememberNavController()
    
    
    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier.background(color = Color.Blue) // Cambia Color.Blue al color que desees
            ){
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    listOfNavItems.forEach{navItem ->
                        NavigationBarItem(
                            selected = currentDestination?.hierarchy?.any {it.route == navItem.route} == true,
                            onClick = {
                                navController.navigate(navItem.route){
                                    popUpTo(navController.graph.findStartDestination().id){
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = navItem.icon,
                                    contentDescription = "")
                            },
                            label = { Text("${navItem.label}") }
                        )
                    }
                }
            }

        }
    ){
        NavHost(
            navController=navController,
            startDestination = Screens.HomeScreen.name,
            modifier = Modifier
                .padding(20.dp)
        ){
            composable(route = Screens.ControllsScreen.name)
            {
                ControllsScreen()
            }
            composable(route = Screens.HomeScreen.name)
            {
                HomeScreen()    
            }
            composable(route = Screens.DevicesScreen.name)
            {
                DevicesScreen(bluetoothController)
            }
            
        }
    }
}