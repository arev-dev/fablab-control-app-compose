package com.arevdevapps.fablabcontrol

import HomeScreen
import android.annotation.SuppressLint
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.arevdevapps.fablabcontrol.Connectivity.BluetoothController
import com.arevdevapps.fablabcontrol.ui.theme.FablabControlTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(bluetoothController: BluetoothController) {
    val navController: NavHostController = rememberNavController()

    FablabControlTheme {
        Scaffold(
            /*topBar = {
                TopAppBar(
                    title = {
                        Text("My App")
                        },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Yellow
                    ))
            }*/
            bottomBar = {
                Box{
                    NavigationBar(
                        containerColor = Color(android.graphics.Color.parseColor("#192C8A"))

                    ) {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination

                        listOfNavItems.forEach{navItem ->
                            NavigationBarItem(

                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = Color(android.graphics.Color.parseColor("#2747E5")),
                                    selectedTextColor = Color(android.graphics.Color.parseColor("#B5E3FF")),
                                    unselectedTextColor = Color.White,
                                    selectedIconColor = Color(android.graphics.Color.parseColor("#B5E3FF")),
                                    unselectedIconColor = Color.White,
                                ),
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
                                label = {
                                    Text(text="${navItem.label}",
                                    )

                                }
                            )
                        }
                    }
                }



            }
        ){
            FablabControlTheme {
                NavHost(
                    navController=navController,
                    startDestination = Screens.HomeScreen.name,
                    modifier = Modifier
                        .padding(20.dp)
                ){
                    composable(route = Screens.ControllsScreen.name)
                    {
                        ControllsScreen(bluetoothController)
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
    }

}