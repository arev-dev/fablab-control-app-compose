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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arevdevapps.fablabcontrol.Connectivity.BluetoothController

//@Preview
@Composable
fun DevicesScreen(bluetoothController: BluetoothController) {
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
        Text("Estado de la conexión: $connectionStatus",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
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
                    bluetoothController.disconnect()
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


        //teste

        if(isButtonClicked)
        {
            if(isConnected)
            {
                SnackDialog("prueba", "jiji", {isButtonClicked = false})
            }
        }

    }

}

@SuppressLint("MissingPermission")
@Composable
fun DeviceItem(device: BluetoothDevice, onDeviceSelected: (String) -> Unit) {
    Card(
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
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
            )
            //Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = device.address,
                style = TextStyle(color = Color.Gray)
            )
        }
    }
}

@Composable
fun SnackDialog(buttonText: String, messageText: String, onClickAction: () -> Unit) {
    SnackbarHost(
        hostState = remember { SnackbarHostState() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Snackbar(
            action = {
                TextButton(onClick = onClickAction) {
                    Text(buttonText)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp)
        ) {
            Text(messageText)
        }
    }
}