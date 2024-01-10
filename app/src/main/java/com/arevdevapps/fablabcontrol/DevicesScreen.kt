@file:OptIn(ExperimentalMaterial3Api::class)

package com.arevdevapps.fablabcontrol

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arevdevapps.fablabcontrol.Connectivity.BluetoothController
import com.arevdevapps.fablabcontrol.ui.theme.FablabControlTheme

//@Preview
@Composable
fun DevicesScreen(bluetoothController: BluetoothController) {
    FablabControlTheme{
        var devices by remember { mutableStateOf<List<BluetoothDevice>>(emptyList()) }
        var connectionStatus by remember { mutableStateOf<String?>("Desconectado") }
        var selectedDevice by remember { mutableStateOf<String?>(null) }
        var commandInput by remember { mutableStateOf(TextFieldValue()) }
        var isConnected by remember { mutableStateOf(false) }

        // Lógica específica de la pantalla de dispositivos aquí...
        devices = bluetoothController.getPairedDevices()
        isConnected = bluetoothController.isBluetoothConnected()
        connectionStatus = if(isConnected) "Conectado" else "Desconectado"


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ){
            var isButtonClicked by remember { mutableStateOf(false) }
            Text("Dispositivos Vinculados",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    color = Color(android.graphics.Color.parseColor("#2E2E2E"))
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text("Selecciona el dispositivo al que te vas a conectar",
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    color = Color(android.graphics.Color.parseColor("#898989"))
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text("$connectionStatus",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Color(android.graphics.Color.parseColor(if(isConnected) "#38E62D" else "#E62D2D"))
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )


            if(!isConnected)
            {

                // UI para mostrar la lista de dispositivos emparejados
                devices.forEach { device ->
                    DeviceItem(device = device, onDeviceSelected = {
                        isConnected = bluetoothController.connectToDevice(it)
                        isButtonClicked = true
                        if(isConnected)
                        {

                        }
                        else{

                        }

                    })
                }

            }

            if(isConnected)
            {
                Button(
                    onClick = {
                        isConnected = !bluetoothController.disconnect()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text("Desconectar")
                }

                TextField(
                    value = commandInput,
                    onValueChange = { commandInput = it },
                    label = { Text("Comando") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                Button(
                    onClick = {
                        val commandText = commandInput.text
                        if (commandText.isNotEmpty()) {
                            bluetoothController.sendCommand(commandText)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text("Enviar Comando")
                }
            }

        }

    }
}

@SuppressLint("MissingPermission")
@Composable
fun DeviceItem(device: BluetoothDevice, onDeviceSelected: (String) -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(android.graphics.Color.parseColor("#2D5FE6")),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onDeviceSelected(device.address) }
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Text(
                text = device.name ?: "Dispostivo Desconocido",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
            )
            //Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = device.address,
                style = TextStyle(color = Color(android.graphics.Color.parseColor("#C4D4FF")),)
            )
        }
    }
}
