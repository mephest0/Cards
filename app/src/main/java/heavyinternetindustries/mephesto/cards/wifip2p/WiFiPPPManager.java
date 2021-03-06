package heavyinternetindustries.mephesto.cards.wifip2p;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Hashtable;

import heavyinternetindustries.mephesto.cards.CardsMessage;
import heavyinternetindustries.mephesto.cards.MainActivity;

/**
 * Created by mephest0 on 18.02.16.
 */
public class WiFiPPPManager {
    public static final int SERVER_PORT = 9002;
    private static WiFiPPPManager pppManager;

    MainActivity activity;
    WifiBroadcastReceiver broadcastReceiver = null;
    IntentFilter intentFilter = null;
    WifiP2pManager p2pManager;
    WifiP2pManager.Channel channel;
    ArrayList<WifiP2pDevice> pppDevices;
    InetAddress ownerAddress;
    Hashtable<String, String> userAdresses;

    public WiFiPPPManager(MainActivity activity) {
        this.activity = activity;
        pppManager = this;
        p2pManager = (WifiP2pManager) activity.getSystemService(Context.WIFI_P2P_SERVICE);
        channel = p2pManager.initialize(activity, activity.getMainLooper(), null);
        pppDevices = new ArrayList<>();
        ownerAddress = null;
        userAdresses = new Hashtable<>();
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

                if (reason == WifiP2pManager.P2P_UNSUPPORTED) System.out.println("P2P unsupported");
                else if (reason == WifiP2pManager.ERROR) System.out.println("Error");
                else if (reason == WifiP2pManager.BUSY) System.out.println("Busy");

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
            for (WifiP2pDevice device : pppDevices) {
                System.out.println(" : " + device.deviceName);
                System.out.println("  : " + device.deviceAddress);
            }

        }
    }

    /*
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
    */

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
                System.out.println("failure connecting.. " + reason);
                if (reason == 0) System.out.println("ERROR");
                if (reason == 2) System.out.println("BUSY");
                if (reason == 1) System.out.println("P2P UNSUPPORTED");
            }
        });

    }

    public void disconnect() {
        p2pManager.removeGroup(channel, null);
    }

    public void clearList() {
        pppDevices.clear();
    }

    public void sendMessage(CardsMessage message) {
        String host = null;
        String uName = message.getOtherEndUsername();
        System.out.println("WiFiPPPManager.sendMessage to: " + uName);

        if (userAdresses.containsKey(uName)) {
            host = userAdresses.get(uName);
        }

        if (host != null) {
            System.out.println("Host address(!): " + host);
            message.prepareForSending();
            sendMessage(host, message.getMessage());
        } else {
            System.out.println("Username not registered");
        }
    }

    private void sendMessage(String host, String message) {
        System.out.println("WiFiPPPManager.sendMessage to: " + host);
        DummyMessageClientTask task = new DummyMessageClientTask(message, host.replace("/", "").trim());

        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void incommingMessage(CardsMessage message) {
        String senderUsername = message.getOtherEndUsername();
        System.out.println("WiFiPPPManager.incommingMessage from: " + senderUsername);

        if (senderUsername != null && !userAdresses.containsKey(senderUsername)) {
            userAdresses.put(senderUsername, message.getOtherEndHost());

            System.out.println("added username: " + senderUsername + " userAdresses.size() = " + userAdresses.size());
            ;
        }

        activity.incomingMessage(message);
    }

    /**
     * Network info changed
     *
     * @param info
     */
    public void setInfo(WifiP2pInfo info) {
        System.out.println("WiFiPPPManager.setInfo");
        System.out.println("info.groupOwnerAddress.toString() = " + info.groupOwnerAddress.toString());
        sendMessage(info.groupOwnerAddress.toString(),
                CardsMessage.registerNewDeviceMessageAsString(info.groupOwnerAddress.toString()));

    }

    public static WiFiPPPManager getPppManager() {
        return pppManager;
    }
}
