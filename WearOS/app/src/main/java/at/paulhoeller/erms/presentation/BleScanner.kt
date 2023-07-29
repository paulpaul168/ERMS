package at.paulhoeller.erms.presentation

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.os.Build
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi

// val bleScanner: BleScanner = BleScanner(LocalContext.current as ComponentActivity, Handler());
//bleScanner.scanLeDevice();

class BleScanner(private val activity: ComponentActivity, private val handler: Handler) {
    private val scanSettings: ScanSettings by lazy {
        val builder = ScanSettings.Builder()

        builder.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).setReportDelay(0)

        builder.build()
    }

    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        val bluetoothManager = activity.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }
    private val bluetoothLeScanner = bluetoothAdapter?.bluetoothLeScanner
    private var scanning = false

    // Stops scanning after 10 seconds.
    private val SCAN_PERIOD: Long = 20000

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.S)
    fun scanLeDevice() {
        if (!scanning) { // Stops scanning after a pre-defined scan period.
            println("Start Scanning...")
            handler.postDelayed({
                scanning = false
                println("Stopped scanning.")
                bluetoothLeScanner?.stopScan(leScanCallback)
            }, SCAN_PERIOD)
            scanning = true
            bluetoothLeScanner?.startScan(null, scanSettings,leScanCallback)
        } else {
            scanning = false
            println("Stopped scanning.")
            bluetoothLeScanner?.stopScan(leScanCallback)
        }
    }
    // Device scan callback.
    private val leScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            //println("Got result: ")
            super.onScanResult(callbackType, result)
            //if(result.scanRecord?.deviceName != null && result.scanRecord?.deviceName != "foobar")
               // println(result.scanRecord?. + ";"+result.rssi + ";" + result.scanRecord?.txPowerLevel)
            println(result.toString());
            //result.txPower
            //if(result.device)
           // println(result.rssi)
            /*leDeviceListAdapter.addDevice(result.device)
            leDeviceListAdapter.notifyDataSetChanged()*/
        }
    }
}

