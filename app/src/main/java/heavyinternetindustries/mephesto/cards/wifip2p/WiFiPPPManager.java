package heavyinternetindustries.mephesto.cards.wifip2p;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;

import java.net.InetAddress;
import java.util.ArrayList;

import heavyinternetindustries.mephesto.cards.CardsMessage;
import heavyinternetindustries.mephesto.cards.MainActivity;

/**
 * Created by mephest0 on 18.02.16.
 */
public class WiFiPPPManager {
    public static final int SERVER_PORT = 9002;
    public static final String GET_USER_FRIENDLY_NAME = "getUserFriendlyName";
    public static final String HERES_USER_FRIENDLY_NAME = "heresUserFriendlyName";
    private static final String REGISTER_MESSAGE = "REGISTER";

    MainActivity activity;
    WifiBroadcastReceiver broadcastReceiver = null;
    IntentFilter intentFilter = null;
    WifiP2pManager p2pManager;
    WifiP2pManager.Channel channel;
    ArrayList<WifiP2pDevice> pppDevices;
    InetAddress ownerAddress;

    public WiFiPPPManager(MainActivity activity) {
        this.activity = activity;
        p2pManager = (WifiP2pManager) activity.getSystemService(Context.WIFI_P2P_SERVICE);
        channel = p2pManager.initialize(activity, activity.getMainLooper(), null);
        pppDevices = new ArrayList<>();
        ownerAddress = null;
}

    public WifiBroadcastReceiver getBR() {
        if (broadcastReceiver == null) {
            broadcastReceiver = new WifiBroadcastReceiver(p2pManager, channel, activity, this);
        }

        return broadcastReceiver;
    }

    public IntentFilter getIF() {
        if (intentFilter == null) {
            intentFilter = new IntentFilter();
            intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
            intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
            intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
            intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
            intentFilter.addAction(WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION);
        }
        return intentFilter;
    }

    public void discoverPeers() {
        p2pManager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                System.out.println("Started discovery");
                activity.setDiscoverableSwitch(true);
            }

            @Override
            public void onFailure(int reason) {
                System.out.println("discovery NOT STARTED, because (reasons): " + reason);
                //TODO if (reason == xxx)
                activity.setDiscoverableSwitch(false);
            }
        });
    }

    public void stopDiscovery() {
        p2pManager.stopPeerDiscovery(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                System.out.println("discovery stopped");
                activity.setDiscoverableSwitch(false);
            }

            @Override
            public void onFailure(int reason) {
                System.out.println("could not stop discovery");
                activity.setDiscoverableSwitch(true);
            }
        });
    }

    public void devicesChanged(WifiP2pDeviceList peers) {
        System.out.println("Devices changed");
        pppDevices.clear();
        pppDevices.addAll(peers.getDeviceList());
        activity.wifiDevices.clear();
        activity.wifiDevices.addAll(peers.getDeviceList());
        activity.invalidateHostList();

        if (pppDevices.isEmpty()) {
            System.out.println("No devices");
        } else {
            System.out.println("List of devices:");
            for (WifiP2pDevice device : pppDevices) System.out.println(" : " + device.deviceName);
        }
    }

    private void connectAndSend(WifiP2pDevice device) {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;

        p2pManager.connect(channel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                System.out.println("connected!!!");
            }

            @Override
            public void onFailure(int reason) {
                System.out.println("didn't connect, because: " + reason);
            }
        });
    }

    public void connectTo(WifiP2pDevice device) {
        System.out.println("WiFiPPPManager.connectTo");
        System.out.println(" name: " + device.deviceName);

        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;

        p2pManager.connect(channel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                System.out.println("connecting...");
            }

            @Override
            public void onFailure(int reason) {
                System.out.printf("failure connecting.. " + reason);
            }
        });

    }

    public void sendToOwner(String text) {
        if (ownerAddress != null) {
            System.out.println("initiate sending: " + text + " | to : " + ownerAddress);

            sendMessage(ownerAddress.getHostAddress(), text);
        } else {
            System.out.println("no one to send to :(");
        }
    }

    public void setOwnerAddress(InetAddress address) {
        ownerAddress = address;
    }

    public void disconnect() {
        p2pManager.removeGroup(channel, null);
    }

    public void clearList() {
        pppDevices.clear();
    }

    public void sendMessage(String host, String message) {
        DummyMessageClientTask task = new DummyMessageClientTask(message, host);

        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void incommingMessage(CardsMessage message) {
        if (message.getRawMessage().equals(REGISTER_MESSAGE)) {
            //first connection

            //check if already registered
        } else {
            //business as usual
        }
    }
}
