package heavyinternetindustries.mephesto.cards.wifip2p;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import heavyinternetindustries.mephesto.cards.CardsMessage;
import heavyinternetindustries.mephesto.cards.MainActivity;

/**
 * Created by mephest0 on 19.02.16.
 */
public class DummyMessageServerTask extends AsyncTask<Void, Void, Void> {
    private final MainActivity activity;

    public DummyMessageServerTask(MainActivity activity) {
        super();
        this.activity = activity;
    }
    @Override
    protected Void doInBackground(Void[] params) {
        StringBuilder message;

        try {
            ServerSocket serverSocket = new ServerSocket(WiFiPPPManager.SERVER_PORT);

            while (!serverSocket.isClosed()) {
                //activity.setServerStatus(true);

                System.out.println("Waiting for incoming connection");
                //blocking
                Socket client = serverSocket.accept();
                //incoming connection

                System.out.println("connection received");
                InputStream inputstream = client.getInputStream();
                Scanner in = new Scanner(inputstream);

                //For maximum performance enhancements, use StringBuilder
                message = new StringBuilder();

                while (in.hasNext()) {
                    message.append(in.nextLine());
                }

                //power level > 9000
                String sender = client.getInetAddress().getHostAddress();
                CardsMessage incomming = new CardsMessage(sender, message.toString(), CardsMessage.MANAGER_WIFIP2P);

                activity.incommingMessage(incomming);
            }

            System.out.println("Closing server socket");
            serverSocket.close();
        } catch (IOException e) {
            //activity.setServerStatus(false);
            System.out.println("############################");
            System.out.println("Server malfunction, W.T.F.!!");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        System.out.println("Server thread ended, this really shouldn't happen");
    }
}
