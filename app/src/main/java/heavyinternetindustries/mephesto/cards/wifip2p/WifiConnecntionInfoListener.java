package heavyinternetindustries.mephesto.cards.wifip2p;

import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;

/**
 * Created by mephest0 on 19.02.16.
 */
public class WifiConnecntionInfoListener implements WifiP2pManager.ConnectionInfoListener {
    WiFiPPPManager manager;

    public WifiConnecntionInfoListener(WiFiPPPManager manager) {
        super();
        this.manager = manager;
    }
    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo info) {
        System.out.println("---MESSAGES FROM THE INTERTUBES:");
        System.out.println(info);
        System.out.println("---TRANSMISSION ENDED");
        manager.setOwnerAddress(info.groupOwnerAddress);
    }
}
