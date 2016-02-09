package heavyinternetindustries.mephesto.cards;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MainActivity extends ActionBarActivity {
    Stack<Pair<String, String>> messages;
    TextView debMessages;

    BluetoothManager bluetoothManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find TextView
        debMessages = (TextView) findViewById(R.id.debug_messages);


        bluetoothManager = new BluetoothManager(this);

        messages = new Stack<>();
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
        bluetoothManager.makeDiscoverable();
    }

    public void scanForDevices(View view) {
        checkIfBluetoothEnabled();

        if (!bluetoothManager.isScanning()) {
            bluetoothManager.scanForDevices();
        } else {
            System.out.println("Printing devices:");
            debMessages.setText("");
            String devList = "";
            for (BluetoothDevice device : bluetoothManager.getDiscoveredDevices()) {
                System.out.println(" + " + device.getName());
                devList += device.getName() + "\n";
            }
            debMessages.setText(devList);
            System.out.println("+");
        }
    }

    public void checkIfBluetoothEnabled() {
        bluetoothManager.checkIfBluetoothEnabled();
    }

    @Override
    public void onPause() {
        super.onPause();
        bluetoothManager.onPause();
    }

    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}