package heavyinternetindustries.mephesto.cards.wifip2p;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import heavyinternetindustries.mephesto.cards.CardsMessage;

/**
 * Created by mephest0 on 09.03.16.
 */
public class WiFiPPPServiceTask extends AsyncTask<Void, Void, Void> {
    WiFiPPPService service;

    public WiFiPPPServiceTask(WiFiPPPService service) {
        this.service = service;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            ServerSocket serverSocket = new ServerSocket(WiFiPPPManager.SERVER_PORT);

            while (!serverSocket.isClosed()) {
                //activity.setServerStatus(true);

                System.out.println("[service task] Waiting for incoming connection");
                //blocking
                Socket client = serverSocket.accept();
                //incoming connection

                System.out.println("[service task] connection received");
                InputStream inputstream = client.getInputStream();
                Scanner in = new Scanner(inputstream);

                //For maximum performance enhancements, use StringBuilder
                StringBuilder message = new StringBuilder();

                while (in.hasNext()) {
                    message.append(in.nextLine());
                }

                //power level > 9000
                String sender = client.getInetAddress().getHostAddress();
                CardsMessage incomming = new CardsMessage(sender, message.toString(), CardsMessage.MANAGER_WIFIP2P);

                service.incommingMessage(incomming);
            }

            System.out.println("Closing server socket");
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
