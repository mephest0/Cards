package heavyinternetindustries.mephesto.cards.wifip2p;

import android.app.Service;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.IBinder;
import android.support.annotation.Nullable;

import heavyinternetindustries.mephesto.cards.CardsMessage;

/**
 * Created by mephest0 on 09.03.16.
 */
public class WiFiPPPService extends Service {
    WiFiPPPServiceTask task;

    public WiFiPPPService() {
    }

    @Override
    public void onCreate() {
        System.out.println("service oncreate");
        task = new WiFiPPPServiceTask(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("service onstartcommand");
        task.execute();
        return START_NOT_STICKY; //or START_STICKY or START_REDELIVER_INTENT
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        System.out.println("service onDestroy");
    }

    public void incommingMessage(CardsMessage message) {
        System.out.println("incomming message");
    }
}
