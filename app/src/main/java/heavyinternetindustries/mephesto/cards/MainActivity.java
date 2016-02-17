package heavyinternetindustries.mephesto.cards;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.ParcelUuid;
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
    String name = "device9000";

    BluetoothManager bluetoothManager;
    BLEManager b4manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Only request permission on Marshmallow
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1234);
        }

        //find TextView
        debMessages = (TextView) findViewById(R.id.debug_messages);

        messages = new Stack<>();
        b4manager = new BLEManager(this);
    }

    public void submitName(View view) {
        name = ((EditText) findViewById(R.id.bt_name_input)).getText().toString();
        String toastText;

        if (name.length() == 0) {
            toastText = "No name entered";
        } else {
            toastText = "Setting name to: " + name;
        }

        b4manager.setName(name);
        Toast toast = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG);
        toast.show();
    }

    public void makeDiscoverable(View view) {
        b4manager.makeDiscoverable();
    }

    public void scanForDevices(View view) {
    }

    public void checkIfBluetoothEnabled() {
    }

    @Override
    public void onPause() {
        System.out.println("onPause()");
        super.onPause();
    }

    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void getIsEnabled(View view) {
        System.out.println("getIsEnabled()");

        System.out.println(": " + b4manager.isEnabled());
    }

    public void enable(View view) {
        System.out.println("enable()");

        b4manager.enable();
    }

    public void startLEScan(View view) {
        b4manager.startScan();
    }

    public void openGates(View view) {
        System.out.println("Create new server");

        b4manager.createServer(name);
    }
}