@file:OptIn(ExperimentalMaterial3Api::class)

package com.arevdevapps.fablabcontrol

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.arevdevapps.fablabcontrol.Connectivity.BluetoothController

//@Preview
@Composable
fun DevicesScreen(bluetoothController: BluetoothController) {
    var devices by remember { mutableStateOf<List<BluetoothDevice>>(emptyList()) }
    var connectionStatus by remember { mutableStateOf<String?>("Offline") }
    var selectedDevice by remember { mutableStateOf<String?>(null) }
    var commandInput by remember { mutableStateOf(TextFieldValue()) }

    // Lógica específica de la pantalla de dispositivos aquí...
    devices = bluetoothController.getPairedDevices()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        // UI para mostrar la lista de dispositivos emparejados
        devices.forEach { device ->
            DeviceItem(device = device, onDeviceSelected = { selectedDevice = it })
        }
        // Botón para conectar al dispositivo seleccionado
        Button(
            onClick = {
                selectedDevice?.let {
                    if (bluetoothController.connectToDevice(it)) {
                       // Toast.makeText(LocalContext.current, "Si jalo", Toast.LENGTH_LONG).show()
                        connectionStatus = "Online"

                    } else {
                        //Toast.makeText(LocalContext.current, "No jalo", Toast.LENGTH_LONG).show()
                        connectionStatus = "Offline"
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Connect to Selected Device")
        }
        Text("Estado de la conexion: $connectionStatus")
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

@SuppressLint("MissingPermission")
@Composable
fun DeviceItem(device: BluetoothDevice, onDeviceSelected: (String) -> Unit) {
    // UI para mostrar un elemento de dispositivo en la lista
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
//            .background(Color.White)
            .clickable { onDeviceSelected(device.address) }
    ) {
        Text(text = device.name ?: "Unknown Device")
        Spacer(modifier = Modifier.height(4.dp))
//        Text(text = device.address)
    }
}