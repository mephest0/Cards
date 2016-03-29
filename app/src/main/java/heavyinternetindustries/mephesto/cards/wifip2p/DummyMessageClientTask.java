package heavyinternetindustries.mephesto.cards.wifip2p;

import android.net.wifi.p2p.WifiP2pDevice;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.spec.PSSParameterSpec;

/**
 * Created by mephest0 on 19.02.16.
 */
public class DummyMessageClientTask extends AsyncTask<Void, Void, Void> {
    public static boolean lock = false;
    String host, message;
    int port = WiFiPPPManager.SERVER_PORT;

    public DummyMessageClientTask(String message, String host) {
        super();
        System.out.println("Creating DummyMessageClientTask");
        this.message = message;
        this.host = host;
        System.out.println("host:" + host);
    }

    @Override
    protected Void doInBackground(Void... params) {
        System.out.println("Starting DummyMessageClientTask");
        Socket socket = new Socket();

        try {
            //avoid sending two packets at the same time
            while (lock) Thread.sleep(2);

            lock = true;

            socket.bind(null);
            socket.connect((new InetSocketAddress(host, port)), 500);

            OutputStream outputStream = socket.getOutputStream();

            PrintStream printStream = new PrintStream(outputStream);
            printStream.print(message);

            printStream.close();
        } catch (IOException e) {
            System.out.println("exception sending message");
            e.printStackTrace();
        } catch (InterruptedException e) {

        }
        finally {
            lock = false;

            if (socket.isConnected()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    //catch logic
                }

            }
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }
}
