package at.paulhoeller.erms.presentation

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat

// val bleScanner: BleScanner = BleScanner(LocalContext.current as ComponentActivity, Handler());
//bleScanner.scanLeDevice();
class BleScanner(private val activity: ComponentActivity, private val handler: Handler) {
    private val REQUEST_BLUETOOTH_PERMISSION = 1
    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        val bluetoothManager = activity.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }
    private val bluetoothLeScanner = bluetoothAdapter?.bluetoothLeScanner
    private var scanning = false

    // Stops scanning after 10 seconds.
    private val SCAN_PERIOD: Long = 10000

    @RequiresApi(Build.VERSION_CODES.S)
    fun scanLeDevice() {
        if (!scanning) { // Stops scanning after a pre-defined scan period.
            println("Start Scanning...")
            handler.postDelayed({
                scanning = false
                if (ActivityCompat.checkSelfPermission(
                        activity,
                        Manifest.permission.BLUETOOTH_SCAN
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    println("Permission not granted")
                    requestPermissions(
                        activity,
                        arrayOf(
                            Manifest.permission.BLUETOOTH_SCAN
                        ),
                        REQUEST_BLUETOOTH_PERMISSION
                    )
                    println("Permission request done")
                    return@postDelayed
                }
                bluetoothLeScanner?.stopScan(leScanCallback)
            }, SCAN_PERIOD)
            scanning = true
            if (ActivityCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.BLUETOOTH_SCAN
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                println("Permission not granted")
                requestPermissions(
                    activity,
                    arrayOf(
                        Manifest.permission.BLUETOOTH_SCAN
                    ),
                    REQUEST_BLUETOOTH_PERMISSION
                )
                println("Permission request done")
                return
            }
            bluetoothLeScanner?.startScan(leScanCallback)
        } else {
            scanning = false
            println("Stopped scanning.")
            bluetoothLeScanner?.stopScan(leScanCallback)
        }
    }
    // Device scan callback.
    private val leScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            println("Got result: ")
            super.onScanResult(callbackType, result)
            println(result.device);
            /*leDeviceListAdapter.addDevice(result.device)
            leDeviceListAdapter.notifyDataSetChanged()*/
        }
    }
}

