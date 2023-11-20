package com.arevdevapps.fablabcontrol


import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.widget.ArrayAdapter
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import java.util.UUID


//const val REQUEST_ENABLE_BT = 1
//var isPermissionGranted = false
//
//lateinit var mBtAdapter: BluetoothAdapter
//var mAddressDevices: ArrayAdapter<String>? = null
//var mNameDevices: ArrayAdapter<String>? = null
//
//
//private object {
//    const val MY_PERMISSIONS_REQUEST_BLUETOOTH = 123
//    const val MY_PERMISSIONS_REQUEST_LOCATION = 124
//    var m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
//    private var m_bluetoothSocket: BluetoothSocket? = null
//    var m_isConnected: Boolean = false
//    lateinit var m_address: String
//}



@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Este es el Inicio")
    }
}