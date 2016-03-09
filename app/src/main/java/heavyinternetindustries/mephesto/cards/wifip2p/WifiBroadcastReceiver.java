package heavyinternetindustries.mephesto.cards.wifip2p;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;

import heavyinternetindustries.mephesto.cards.MainActivity;

/**
 * Created by mephest0 on 18.02.16.
 */
public class WifiBroadcastReceiver extends BroadcastReceiver{
    WifiP2pManager manager;
    WifiP2pManager.Channel channel;
    WiFiPPPManager pppManager;
    MainActivity activity;
    WifiP2pManager.PeerListListener peerListListener;

    public WifiBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, MainActivity activity, WiFiPPPManager pppManager) {
        super();
        this.manager = manager;
        this.channel = channel;
        this.activity = activity;
        this.pppManager = pppManager;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Check to see if Wi-Fi is enabled and notify appropriate activity
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);

            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                //p2p enabled
                System.out.println("p2p enabled");
            } else {
                //p2p not enabled
                System.out.println("p2p not enabled");
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // Call WifiP2pManager.requestPeers() to get a list of current peers
            System.out.println("peers changed");
            if (manager != null) {
                peerListListener = new WifiP2pManager.PeerListListener() {
                    @Override
                    public void onPeersAvailable(WifiP2pDeviceList peers) {
                        pppManager.devicesChanged(peers);
                    }
                };

                manager.requestPeers(channel, peerListListener);
            }
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Respond to new connection or disconnections
            System.out.println("Connection changed");

            if (manager == null) {
                return;
            }

            NetworkInfo info = (NetworkInfo) intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

            if (info.isConnected()) {
                //connected to other device
                System.out.println("isConnected");

                manager.requestConnectionInfo(channel, new WifiConnecntionInfoListener(pppManager));
            }

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            // Respond to this device's wifi state changing
        } else if (WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION.equals(action)) {
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_DISCOVERY_STATE, -1);

            if (state == WifiP2pManager.WIFI_P2P_DISCOVERY_STARTED) {
                System.out.println("confirmed discovery started");
                activity.setDiscoverableSwitch(true);
            } else if (state == WifiP2pManager.WIFI_P2P_DISCOVERY_STOPPED) {
                System.out.println("confirmed discovery stopped");
                activity.setDiscoverableSwitch(false);
            } else {
                System.out.println("summink wrong");
            }
        }
    }
}
