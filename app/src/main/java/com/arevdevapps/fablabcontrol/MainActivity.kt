package com.arevdevapps.fablabcontrol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
//import androidx.compose.foundation.gestures.ModifierLocalScrollableContainerProvider.value
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.widget.ArrayAdapter
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import java.util.*
import android.Manifest
import android.annotation.SuppressLint
import android.app.ActivityOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.arevdevapps.fablabcontrol.Connectivity.BluetoothController
import com.arevdevapps.fablabcontrol.ui.theme.FablabControlTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.selects.select


class MainActivity : ComponentActivity() {
    private val bluetoothController by lazy { BluetoothController(this) }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FablabControlTheme {
                getPermissions()
                AppNavigation(bluetoothController)
            }
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun getPermissions() {
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    LaunchedEffect(true){
        permissionState.launchMultiplePermissionRequest()

    }
    if (!permissionState.allPermissionsGranted) {
        AlertDialog(
            title = { Text("Permisos insuficientes") },
            text = { Text("Algunos permisos no fueron concedidos. Por favor, concede los permisos manualmente desde ajustes.") },
            onDismissRequest = {
                // Acción al cerrar el diálogo (puedes dejarlo vacío si no necesitas realizar ninguna acción específica)
            },
            confirmButton = {

            }
        )
    }
}










