package heavyinternetindustries.mephesto.cards;

import android.Manifest;
import android.annotation.TargetApi;
import android.bluetooth.*;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.ParcelUuid;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.nio.charset.Charset;
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
    public static final String B4UUID = "9d10684c-d644-11e5-ab30-625662870761";

    String deviceName;
    BluetoothLeAdvertiser advertiser;
    AdvertiseCallback advertiseCallback;
    BluetoothGattServer server;
    BluetoothGattService service = new BluetoothGattService(
            UUID.fromString(B4UUID),
            BluetoothGattService.SERVICE_TYPE_SECONDARY);
    private MainActivity activity;
    BluetoothAdapter bluetoothAdapter;
    private boolean isScanning;
    public boolean isAdvertising;
    private BluetoothLeScanner scanner;
    private android.bluetooth.BluetoothManager bluetoothManager;

    public BLEManager(MainActivity activity) {
        this.activity = activity;
        isScanning = false;
        isAdvertising = false;

        deviceName = "device1337";

        bluetoothManager = (android.bluetooth.BluetoothManager) activity.getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();

        if (bluetoothAdapter == null) try {
            throw new Exception("Turn on Bluetooth dickhead");
        } catch (Exception e) {
            System.err.println("Could not throw exception");
        }

        System.out.println("Bluetooth LE manager created");
        System.out.println(" multiple advertisement: " + bluetoothAdapter.isMultipleAdvertisementSupported());
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
            System.out.println("B4Manager: name changed");
            activity.toast("name changed");
        }
        else System.out.println("B4Manager: name not changed");

        deviceName = name;
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
                //settings.setReportDelay(1000L);

                //scanner.startScan(filters, settings.build(), this);
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
        System.out.println("onScanResult()");
        System.out.println("  Name: " + result.getDevice().getName());

        if (result.getScanRecord() != null && result.getDevice() != null) {
            System.out.println("  DeviceName: " + result.getScanRecord().getDeviceName());
            System.out.println("  toString: " + result.toString());
            System.out.println("  address: " + result.getDevice().getAddress());
            if (result.getDevice().getUuids() != null) {
                System.out.println("  UUIDs:");
                for (ParcelUuid id : result.getDevice().getUuids())
                    System.out.println("   - " + id.toString());
            }
        }

        BluetoothDevice scanDevice = result.getDevice();
        //System.out.println("  started service discovery: " + scanDevice.fetchUuidsWithSdp());
    }

    public void connect(BluetoothDevice device) {
//        while (device.getName() == null) ;
    }

    @Override
    public void onBatchScanResults(List<ScanResult> results) {
        System.out.println("on batch scan results");
    }

    @Override
    public void onScanFailed(int errorCode) {
        System.out.println("Scan failed!! :(");
    }

    public void makeDiscoverable() {
        System.out.println("make discoverable");
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120);
        activity.startActivity(discoverableIntent);
    }

    public void startAdvertising() {
        System.out.println("Start advertising");

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_ADMIN) !=
                PackageManager.PERMISSION_GRANTED) {
            System.out.println(" doesn't have permission");

        } else {
            System.out.println(" has permission");
            advertiser = bluetoothAdapter.getBluetoothLeAdvertiser();
            AdvertiseSettings.Builder settings = new AdvertiseSettings.Builder();
            settings.setConnectable(true);
            settings.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY);
            settings.setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH);
            settings.setTimeout(120000);
            AdvertiseData.Builder data = new AdvertiseData.Builder();
            data.addServiceUuid(new ParcelUuid(UUID.fromString(B4UUID)));
            data.addServiceData(new ParcelUuid(UUID.fromString(B4UUID)), "a".getBytes(Charset.forName("UTF-8")));
            data.setIncludeDeviceName(true);
            advertiseCallback = new AdvertiseBack();
            advertiser.startAdvertising(settings.build(), data.build(), advertiseCallback);
            isAdvertising = true;
        }
    }

    public void stopAdvertising() {
        System.out.println("stop advertising");
        if (isAdvertising) advertiser.stopAdvertising(advertiseCallback);
        isAdvertising = false;
    }

    public void createServer() {
        server = bluetoothManager.openGattServer(activity, new Callback());

        service = new BluetoothGattService(
                UUID.fromString(B4UUID),
                BluetoothGattService.SERVICE_TYPE_SECONDARY);
        server.addService(service);
        System.out.println("server: " + server);
    }

    private class Callback extends BluetoothGattServerCallback {
        public Callback() {
            System.out.println("Created callback");
        }

        @Override
        public void onCharacteristicReadRequest(BluetoothDevice device,
                                                int requestId,
                                                int offset,
                                                BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicReadRequest(device, requestId, offset, characteristic);
            System.out.println("onCharacteristicReadRequest " + characteristic.getUuid().toString());
        }
    }

    private class AdvertiseBack extends AdvertiseCallback {
        public AdvertiseBack() {
            System.out.println("created advertise callback");
        }

        public void onStartFailure(int errorCode) {
            super.onStartFailure(errorCode);
            System.out.println("AC start failure, error code: " + errorCode);
            stopAdvertising();
        }

        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            super.onStartSuccess(settingsInEffect);
            System.out.println("AC start success");
        }

    }
}
