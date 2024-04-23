package com.arevdevapps.fablabcontrol


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arevdevapps.fablabcontrol.Connectivity.BluetoothController
import com.arevdevapps.fablabcontrol.JoyStick.JoyStick

@Composable
fun ControllsScreen(bluetoothController: BluetoothController) {
    var joy by remember { mutableStateOf<String?>("") }
    var isBluetoothConnected by remember { mutableStateOf<Boolean>(false) }

    isBluetoothConnected = bluetoothController.isBluetoothConnected()

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (isBluetoothConnected) "Conexión Establecida" else "Conéctate a un dispositivo para usar el JoyStick",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    color = Color(android.graphics.Color.parseColor("#2E2E2E"))
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                if (isBluetoothConnected) {
                    JoyStick(
                        Modifier.padding(30.dp),
                        size = 200.dp,
                        dotSize = 70.dp
                    ) { x: Float, y: Float, combined: Int ->
                        joy = "X: $x, Y: $y, Robot: $combined"
                        bluetoothController.sendCommand(combined.toString())
                        Log.d("JoyStick", "$x, $y")
                    }
                }
            }
        }
    }
}