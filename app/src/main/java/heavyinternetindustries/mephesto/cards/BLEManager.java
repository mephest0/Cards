package heavyinternetindustries.mephesto.cards;

import android.bluetooth.*;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.os.ParcelUuid;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

/**
 * Created by mephest0 on 09.02.16.
 */
public class BLEManager extends ScanCallback {
    private static final int REQUEST_ENABLE_BT = 1337;
    public static final String B4UUID = "6bba20f1-2876-45b3-a35d-141b6b2983fe";

    BluetoothGattServer server;
    BluetoothGattService service = new BluetoothGattService(
            UUID.fromString(B4UUID),
            BluetoothGattService.SERVICE_TYPE_SECONDARY);
    private MainActivity activity;
    BluetoothAdapter bluetoothAdapter;
    private boolean isScanning;
    private BluetoothLeScanner scanner;
    private android.bluetooth.BluetoothManager bluetoothManager;

    public BLEManager(MainActivity activity) {
        this.activity = activity;
        isScanning = false;

        bluetoothManager = (android.bluetooth.BluetoothManager) activity.getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();

        if (bluetoothAdapter == null) try {
            throw new Exception("Turn on Bluetooth dickhead");
        } catch (Exception e) {
            System.err.println("Could not throw exception");
        }

        System.out.println("Bluetooth LE manager created");
    }

    public boolean isEnabled() {
        return bluetoothAdapter.isEnabled();
    }

    public void enable() {
        db("enable Bluetooth");
        if (!isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    private void db(String s) {
        System.out.println(" (b4m) " + s);
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

                ScanFilter.Builder filter = new ScanFilter.Builder();
                filter.setServiceUuid(new ParcelUuid(UUID.fromString(B4UUID)));
                ArrayList<ScanFilter> filters = new ArrayList<>();
                filters.add(filter.build());

                ScanSettings.Builder settings = new ScanSettings.Builder();
                //settings.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY);

                scanner.startScan(filters, settings.build(), this);
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

    public void createServer(String name) {
        server = bluetoothManager.openGattServer(activity, new Callback());
        server.addService(service);
    }

    private class Callback extends BluetoothGattServerCallback {
        public Callback() {
            System.out.println("Created callback");
        }
    }
}
