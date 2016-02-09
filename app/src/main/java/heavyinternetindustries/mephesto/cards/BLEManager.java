package heavyinternetindustries.mephesto.cards;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;

import java.util.List;

/**
 * Created by mephest0 on 09.02.16.
 */
public class BLEManager extends ScanCallback {
    private static final int REQUEST_ENABLE_BT = 1337;
    private MainActivity activity;
    BluetoothAdapter bluetoothAdapter;
    private boolean isScanning;
    private BluetoothLeScanner scanner;

    public BLEManager(MainActivity activity) {
        this.activity = activity;
        isScanning = false;

        final android.bluetooth.BluetoothManager bluetoothManager =
                (android.bluetooth.BluetoothManager) activity.getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();

        if (bluetoothAdapter == null) try {
            throw new Exception("Turn on Bluetooth dickhead");
        } catch (Exception e) {
            System.err.println("Could not throw exception");
        }
    }

    public boolean isEnabled() {
        return bluetoothAdapter.isEnabled();
    }

    public void enable() {
        if (!isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    public void setName(String name) {
        if (bluetoothAdapter.setName(name)) {
            System.out.println("name changed");
            activity.toast("name changed");
        }
        else System.out.println("name not changed");
    }

    public void startScan() {
        if (isEnabled()) {
            if (!isScanning) {
                System.out.println("starting scan");
                scanner = bluetoothAdapter.getBluetoothLeScanner();
                scanner.startScan(this);
                isScanning = true;
            } else {
                System.out.println("stopping scan");
                isScanning = false;
                scanner.flushPendingScanResults(this);
                scanner.stopScan(this);
            }
        } else {
            System.out.println("BLE not enabled");
        }
    }

    @Override
    public void onScanResult(int callbackType, ScanResult result) {
        System.out.println("onscanresult: " + result.getDevice().getName());
        System.out.println(" : " +  result.toString());
    }

    @Override
    public void onBatchScanResults(List<ScanResult> results) {
        System.out.println("on batch scan results");
    }

    public void makeDiscoverable() {
        System.out.println("make discoverable");
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120);
        activity.startActivity(discoverableIntent);
    }
}
