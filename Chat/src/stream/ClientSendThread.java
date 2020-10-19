/***
 * ClientSendThread (CLIENT SIDE).
 * Thread that reads messages from console and sends them to client side socket.
 * @since 13/10/20
 * @author Stefan Ristovksi Aydin Akaydin
 */

package stream;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientSendThread
        extends Thread {

    /**
     * Client socket
     **/
    private Socket clientSocket;

    ClientSendThread(Socket s) {
        this.clientSocket = s;
    }

    /**
     * Reads the line from std input and sends the lines to the client socket.
     **/
    public void run() {
        try {
            BufferedReader stdIn = null;

            stdIn = new BufferedReader(new InputStreamReader(System.in));

            PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
            while (true) {
                String line=stdIn.readLine();
                socOut.println(line);
            }
        } catch (Exception e) {
            System.err.println("Error in Client Send Thread:" + e);
        }
    }

}
