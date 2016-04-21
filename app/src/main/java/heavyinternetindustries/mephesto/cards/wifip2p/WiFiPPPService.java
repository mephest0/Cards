package heavyinternetindustries.mephesto.cards.wifip2p;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import heavyinternetindustries.mephesto.cards.CardsMessage;
import heavyinternetindustries.mephesto.cards.MainActivity;

/**
 * Created by mephest0 on 09.03.16.
 */
public class WiFiPPPService extends IntentService {
    private final WiFiPPPManager manager;

    public WiFiPPPService() {
        super("Cards WiFi server (zero argument constructor)");
        manager = WiFiPPPManager.getPppManager();
        if (manager == null) System.err.println("MANAGER == NULL");
    }

    public WiFiPPPService(WiFiPPPManager manager) {
        super("Cards WiFi server");
        this.manager = manager;
    }

    public void incommingMessage(CardsMessage message) {
        manager.incommingMessage(message);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            ServerSocket serverSocket = new ServerSocket(WiFiPPPManager.SERVER_PORT);

            while (!serverSocket.isClosed()) {
                //activity.setServerStatus(true);

                System.out.println("[service] Waiting for incoming connection");
                //blocking
                Socket client = serverSocket.accept();
                //incoming connection

                System.out.println("[service] connection received");
                InputStream inputstream = client.getInputStream();
                Scanner in = new Scanner(inputstream);

                //For maximum performance enhancements, use StringBuilder
                StringBuilder message = new StringBuilder();

                while (in.hasNext()) {
                    message.append(in.nextLine());
                }

                //power level > 9000
                String senderHostAddress = client.getInetAddress().getHostAddress();
                CardsMessage incomming = new CardsMessage(senderHostAddress, message.toString(), CardsMessage.MANAGER_WIFIP2P);

                incommingMessage(incomming);

                client.close();
            }

            System.out.println("Closing server socket");
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("SERVER SERVICE FAILED");
            e.printStackTrace();
        }
    }
}
