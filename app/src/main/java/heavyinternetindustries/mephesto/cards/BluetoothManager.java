package heavyinternetindustries.mephesto.cards;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Pair;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by mephest0 on 09.02.16.
 */
public class BluetoothManager {
    BluetoothAdapter bluetoothAdapter;
    ArrayList<BluetoothDevice> discoveredDevices;
    MainActivity activity;
    private boolean isScanning;

    public BluetoothManager(MainActivity activity) {
        this.activity = activity;
        isScanning = false;
        discoveredDevices = new ArrayList<>();

        //find BluetoothManager
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Toast toast;
        if (bluetoothAdapter == null) {
            //no bluetooths
            toast = Toast.makeText(activity.getApplicationContext(), "No bluetooth", Toast.LENGTH_LONG);
        } else {
            //all good
            toast = Toast.makeText(activity.getApplicationContext(), "Bluetooth initialized", Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public void onResume() {

    }

    public void onPause() {
        //stuff goes here

        if (isScanning()) activity.unregisterReceiver(mReceiver);
    }

    public boolean isEnabled() {
        return bluetoothAdapter.isEnabled();
    }

    /**
     * Initiates a scan. Discovered devices are stored in a list.
     * @See getDiscoveredDevices()
     */
    ArrayList<Pair<String, String>> scanForDevices() {
        System.out.println("scanForDevices()");

        if (isEnabled()) {
            System.out.println("scanning for devices");
            IntentFilter filter = new IntentFilter();

            filter.addAction(BluetoothDevice.ACTION_FOUND);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

            activity.registerReceiver(mReceiver, filter);

            bluetoothAdapter.startDiscovery();
        } else {
            System.out.println("bluetooth not enabled");
            return new ArrayList<>();
        }

        return null;
    }

    ArrayList<BluetoothDevice> getDiscoveredDevices() {
        return discoveredDevices;
    }

    public void makeDiscoverable() {
        System.out.println("make discoverable");
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120);
        activity.startActivity(discoverableIntent);
    }

    public void checkIfBluetoothEnabled() {
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent, 123); //Request code (for activity result) goes here
        } else {

        }
    }

    public boolean isScanning() {
        return isScanning;
    }

    private void scanStarted() {
        System.out.println("Starting scan");
        isScanning = true;
        discoveredDevices.clear();
    }

    private void scanFinished() {
        System.out.println("Scan finished");
        isScanning = false;
        activity.unregisterReceiver(mReceiver);

        System.out.println("Printing discovered devices:");
        for (BluetoothDevice device : discoveredDevices)
            System.out.println(" + " + device.getName());
        System.out.println("-");
    }

    //Intent receiver
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                scanStarted();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                scanFinished();
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //bluetooth device found
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                discoveredDevices.add(device);
                System.out.println("Found device " + device.getName());
            }
        }
    };

    public void initiateServerSocket() {

    }
}
