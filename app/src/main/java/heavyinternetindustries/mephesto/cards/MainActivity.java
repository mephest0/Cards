package heavyinternetindustries.mephesto.cards;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDevice;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import heavyinternetindustries.mephesto.cards.wifip2p.WiFiPPPManager;
import heavyinternetindustries.mephesto.cards.wifip2p.WiFiPPPService;

public class MainActivity extends ActionBarActivity {
    ArrayList<CardsMessage> messages;
    public ArrayList<WifiP2pDevice> wifiDevices;
    ListView hostList;
    WiFiPPPManager wifiManager;
    WiFiPPPService service;
    HostListAdapter hostListAdapter;
    Hashtable<String, Integer> userManagers;
    static String username;
    GameManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messages = new ArrayList<>();
        manager = null;
        wifiManager = new WiFiPPPManager(this);
        service = new WiFiPPPService(wifiManager);
        wifiDevices = new ArrayList<>();
        username = (new Random()).nextInt() + "name";
        ((TextView)findViewById(R.id.username)).setText(username);

        userManagers = new Hashtable<>();

        hostListAdapter = new HostListAdapter(this);
        hostList = (ListView) findViewById(R.id.host_list);
        hostList.setAdapter(hostListAdapter);

        hostList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("clicked: " + position);
                System.out.println("name: " + wifiDevices.get(position).deviceName);

                wifiManager.connectTo(wifiDevices.get(position));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(wifiManager.getBR(), wifiManager.getIF());
        System.out.println("starting service");
        startService(new Intent(this, WiFiPPPService.class));

        wifiManager.discoverPeers();
    }

    @Override
    public void onPause() {
        System.out.println("onPause()");
        super.onPause();

        unregisterReceiver(wifiManager.getBR());
        wifiManager.stopDiscovery();
    }

    /**
     * for debug purposes only
     * @param view View
     */
    public void onClickOk(View view) {
        System.out.println("MainActivity.onClickOk");
        String text = ((EditText) findViewById(R.id.bt_name_input)).getText().toString();

        System.out.println("userManagers.size() = " + userManagers.size());
        for (String receiver : userManagers.keySet()) {
            System.out.println("Sending to: " + receiver);
            sendMessage(new CardsMessage(receiver, 100, MainActivity.getUsername(), "statetates", "changes", text));
        }
    }

    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void clickedDiscoveringSwitch(View view) {
        System.out.println("MainActivity.clickedDiscoveringSwitch");
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

    public void incomingMessage(CardsMessage cardsMessage) {
        String senderUsername = cardsMessage.getOtherEndUsername();
        System.out.println("MainActivity.incomingMessage from: " + senderUsername);

        if (senderUsername != null && !userManagers.containsKey(senderUsername)) {
            userManagers.put(senderUsername, cardsMessage.getManager());

            System.out.println("New username, sending register message");
            sendMessage(CardsMessage.registerNewDeviceMessage(senderUsername));
        }

        if (cardsMessage.getTick() != -1) {
            //messages.add(cardsMessage);
            if (manager != null) {
                //business as usual
                manager.updateFromNetwork(cardsMessage);
            } else if (cardsMessage.getTick() == 1) {
                //first update from host
                prepareGameManager(cardsMessage);

                manager.updateFromNetwork(cardsMessage);
            } else {
                System.out.println("ping");
            }

        }
    }

    private void prepareGameManager(CardsMessage cardsMessage) {
        //first update from host
        String usernamesString = cardsMessage.getExtra(GameManager.PLAYER_LIST_EXTRA_ID);
        String[] usernamesArray = usernamesString.split(CardsMessage.MESSAGE_CARD_SEPARATOR);

        ArrayList<String> usernames = new ArrayList<>();
        for (String name : usernamesArray)
            usernames.add(name);

        GameSetup setup = new GameSetup("Name from network (TODO)",
                usernames,
                cardsMessage.getOtherEndUsername(),
                getUsername());

        manager = new GameManager(setup, this, new PokerRules(setup));


    }

    public void sendMessage(CardsMessage cardsMessage) {
        String uName = cardsMessage.getOtherEndUsername();

        if (userManagers.containsKey(uName)) {
            int link = userManagers.get(uName);
            if (link == CardsMessage.MANAGER_WIFIP2P) wifiManager.sendMessage(cardsMessage);
        } else {
            System.out.println("Name not found - names in list:");
            for (String name : userManagers.keySet()) System.out.println(" : " + name);
        }
    }

    public void onClickDisableWifi(View view) {
        wifiManager.disconnect();
        wifiManager.clearList();
        wifiManager.stopDiscovery();
    }

    public void createNewGame(View view) {
        wifiManager.discoverPeers();

    }

    public void invalidateHostList() {
        //findViewById(R.id.host_list).invalidate();
        hostList.setAdapter(hostListAdapter);
    }

    public void invalidateHostList(View view) {
        hostList.invalidate();
    }

    public static String getUsername() {
        return username;
    }

    public void onClickStartGame(View view) {
        System.out.println("MainActivity.onClickStartGame");

        ArrayList<String> users = new ArrayList<>();

        for (String name : userManagers.keySet())
            users.add(name);

        GameSetup setup = new GameSetup("testgame", users, getUsername(), getUsername());

        manager = new GameManager(setup, this, new PokerRules(setup));

        manager.startGame();


    }
}