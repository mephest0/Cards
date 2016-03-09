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
            System.out.println("waiting");
            while (lock) Thread.sleep(2);

            lock = true;
            /**
             * Create a client socket with the host,
             * port, and timeout information.
             */
            System.out.println("binding");
            socket.bind(null);
            System.out.println("connecting");
            socket.connect((new InetSocketAddress(host, port)), 500);

            /**
             * Create a byte stream from a JPEG file and pipe it to the output stream
             * of the socket. This data will be retrieved by the server device.
             */
            System.out.println("getting stream(s)");
            OutputStream outputStream = socket.getOutputStream();

            PrintStream printStream = new PrintStream(outputStream);
            System.out.println("sending");
            printStream.print(message);

            printStream.close();
            System.out.println("message sendt");
        } catch (IOException e) {
            System.out.println("exception sending message");
            e.printStackTrace();
        } catch (InterruptedException e) {

        }

/**
 * Clean up any open sockets when done
 * transferring or if an exception occurred.
 */
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
