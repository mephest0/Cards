package heavyinternetindustries.mephesto.cards;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import heavyinternetindustries.mephesto.cards.wifip2p.WiFiPPPManager;
import heavyinternetindustries.mephesto.cards.wifip2p.WiFiPPPService;

public class MainActivity extends ActionBarActivity {
    ArrayList<CardsMessage> messages;
    TextView debMessages;
    String name = "device9000";
    WiFiPPPManager wifiManager;
    WiFiPPPService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messages = new ArrayList<>();
        service = new WiFiPPPService();

        //find TextView
        debMessages = (TextView) findViewById(R.id.debug_messages);

        wifiManager = new WiFiPPPManager(this);

        setServerStatus(false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(wifiManager.getBR(), wifiManager.getIF());
        System.out.println("starting service");
        startService(new Intent(this, WiFiPPPService.class));
    }

    public void sendText(View view) {
        String text = ((EditText) findViewById(R.id.bt_name_input)).getText().toString();
        String toastText;

        if (text.length() == 0) {
            toastText = "No text entered";
        } else {
            toastText = "Sending: " + text;
        }

        Toast toast = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG);
        toast.show();

        wifiManager.sendToOwner(text);
    }

    @Override
    public void onPause() {
        System.out.println("onPause()");
        super.onPause();

        unregisterReceiver(wifiManager.getBR());
    }

    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void discoverPeers(View view) {
        wifiManager.discoverPeers();
    }

    public void clickedDiscoverableSwitch(View view) {
        System.out.println("clicked discoverable");
        Switch toggle = (Switch) view;

        toggle.setEnabled(false);

        if (toggle.isChecked()) {
            wifiManager.discoverPeers();
        } else {
            wifiManager.stopDiscovery();
        }
    }

    public void setDiscoverableSwitch(boolean onOff) {
        Switch toggle = (Switch) findViewById(R.id.switch1);
        toggle.setChecked(onOff);
        toggle.setEnabled(true);
    }

    public void setConnectButtonEnabled(boolean enabled) {
        findViewById(R.id.connectFirstButton).setEnabled(enabled);
    }

    public void connectToFirst(View view) {
        System.out.println("Connect to first");
        wifiManager.connectToFirst();
    }

    public void setDebugBox(String text) {
        debMessages.setText(text);
    }

    public void setServerStatus(boolean b) {
        TextView tv = (TextView) findViewById(R.id.server_status);
        tv.setText(b ? "listening" : "not listening");
    }

    public void executeTask(AsyncTask task) {
        task.execute();
    }

    public void incommingMessage(CardsMessage cardsMessage) {
        System.out.println("incomming message:");
        System.out.println(cardsMessage);
        if (cardsMessage.getMessage().equals(WiFiPPPManager.GET_USER_FRIENDLY_NAME)) {
            System.out.println(".name request");
            wifiManager.sendMessage(cardsMessage.getOtherEnd(), WiFiPPPManager.HERES_USER_FRIENDLY_NAME + "myname");
        } else if (cardsMessage.getMessage().startsWith(WiFiPPPManager.HERES_USER_FRIENDLY_NAME)) {
            System.out.println("got user name: " + cardsMessage.getMessage());
            String inName = cardsMessage.getMessage().replace(WiFiPPPManager.HERES_USER_FRIENDLY_NAME, "");
            System.out.println(" : name: " + inName);
        }

        else {
            messages.add(cardsMessage);
        }
    }

    public void resetConnections(View view) {
        wifiManager.disconnect();
        wifiManager.clearList();
        wifiManager.stopDiscovery();
    }

    public void createNewGame(View view) {
        wifiManager.discoverPeers();

    }
}