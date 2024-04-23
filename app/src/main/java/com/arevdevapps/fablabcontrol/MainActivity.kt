package com.arevdevapps.fablabcontrol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import java.util.*
import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import com.arevdevapps.fablabcontrol.Connectivity.BluetoothController
import com.arevdevapps.fablabcontrol.ui.theme.FablabControlTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import androidx.core.app.ActivityCompat
import androidx.activity.result.contract.ActivityResultContracts
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState


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
    val permissionState = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
        rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ))
    }else{
        rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            ))
    }


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













