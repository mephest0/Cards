package heavyinternetindustries.mephesto.cards;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
    TextView debMessages;
    BluetoothAdapter bluetoothAdapter;

    //Intent receiver
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                System.out.println("Starting scan");
                setScanStarted();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                System.out.println("Scan finished");
                setScanFinished();
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //bluetooth device found
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                System.out.println("Found device " + device.getName());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find TextView
        debMessages = (TextView) findViewById(R.id.debug_messages);

        //find BluetoothManager
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Toast toast;
        if (bluetoothAdapter == null) {
            //no bluetooths
            toast = Toast.makeText(getApplicationContext(), "No bluetooth", Toast.LENGTH_LONG);
        } else {
            //all good
            toast = Toast.makeText(getApplicationContext(), "Bluetooth initialized", Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public void setScanStarted() {
        ((Button) findViewById(R.id.scan)).setEnabled(false);
    }

    public void setScanFinished() {
        ((Button) findViewById(R.id.scan)).setEnabled(true);
    }

    public void submitName(View view) {
        String name = ((EditText) findViewById(R.id.bt_name_input)).getText().toString();
        String toastText;

        if (name.length() == 0) {
            toastText = "No name entered";
        } else {
            toastText = "Hi, " + name + "!";
        }

        Toast toast = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG);
        toast.show();
    }

    public void makeDiscoverable(View view) {
        makeDiscoverable();
    }

    public void makeDiscoverable() {
        System.out.println("make discoverable");
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120);
        startActivity(discoverableIntent);
        System.out.println("made discoverable");
    }

    public void scanForDevices(View view) {
        checkIfBluetoothEnabled();

        IntentFilter filter = new IntentFilter();

        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        registerReceiver(mReceiver, filter);
        bluetoothAdapter.startDiscovery();
    }

    public void startNewHost(String name) {
        //TODO
    }

    public void checkIfBluetoothEnabled() {
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 123); //Request code goes here
        }

    }

    @Override
    public void onPause() {
        unregisterReceiver(mReceiver);
        super.onPause();
    }

    public void bacon(){
        System.out.println("tasty tasty for fuck sake");
    }
}