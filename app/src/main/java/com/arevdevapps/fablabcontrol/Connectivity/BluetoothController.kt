package com.arevdevapps.fablabcontrol.Connectivity

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.ArrayAdapter
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import java.io.IOException
import java.util.*
import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.runtime.LaunchedEffect
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi

class BluetoothController(private val context: Context) {
    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        val bluetoothManager =
            context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager?
        bluetoothManager?.adapter
    }

    private var bluetoothSocket: BluetoothSocket? = null
    private var isConnected: Boolean = false
    private var selectedDeviceAddress: String? = null

    companion object {
        const val MY_PERMISSIONS_REQUEST_BLUETOOTH = 123
        const val MY_PERMISSIONS_REQUEST_LOCATION = 124
        val MY_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    }

    fun checkBluetoothPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.BLUETOOTH_CONNECT
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }



    fun isBluetoothEnabled(): Boolean {
        return bluetoothAdapter?.isEnabled == true
    }

    fun isBluetoothConnected(): Boolean {
        return bluetoothSocket?.isConnected == true
    }

   // @SuppressLint("MissingPermission")
   @SuppressLint("MissingPermission")
   @OptIn(ExperimentalPermissionsApi::class)
   fun enableBluetooth() {

        if (!isBluetoothEnabled()) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)

            context.startActivity(enableBtIntent)
        }
    }

   @SuppressLint("MissingPermission")
    fun disableBluetooth() {
        bluetoothAdapter?.disable()
    }

    @SuppressLint("MissingPermission")
    fun getPairedDevices(): List<BluetoothDevice> {
        return bluetoothAdapter?.bondedDevices?.toList() ?: emptyList()
    }

    fun disconnect() : Boolean {
        return try {
            bluetoothSocket?.close()
            isConnected = false
            selectedDeviceAddress = null
            true
        } catch (e: IOException) {
            Log.e("BluetoothController", "Error disconnecting: ${e.message}")
            false
        }
    }




    @SuppressLint("MissingPermission")
    fun connectToDevice(deviceAdress: String): Boolean {
        try {
            bluetoothAdapter?.cancelDiscovery()
            val device: BluetoothDevice? = bluetoothAdapter?.getRemoteDevice(deviceAdress)
            bluetoothSocket = device?.createRfcommSocketToServiceRecord(MY_UUID)
            try{
                bluetoothSocket?.connect()
                isConnected = true
                selectedDeviceAddress = deviceAdress
            }catch(e: IOException){
                return false
            }
            return true
        } catch (e: IOException) {
            return false
        }
    }


    fun sendCommand(command: String) {
        try {
            val outputStream = bluetoothSocket?.outputStream
            outputStream?.write(command.toByteArray())
        } catch (e: IOException) {
            Log.e("BluetoothController", "Error sending command: ${e.message}")
        }
    }


}