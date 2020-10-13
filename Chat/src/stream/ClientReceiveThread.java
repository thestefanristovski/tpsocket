/***
 * ClientReceiveThread (CLIENT SIDE)
 * Thread that reads incoming messages from socket and prints them on screen
 * Date: 13/10/20
 * Authors: Stefan Ristovksi Aydin Akaydin
 */

package stream;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientReceiveThread
        extends Thread {

    private Socket clientSocket;

    ClientReceiveThread(Socket s) {
        this.clientSocket = s;
    }

    /**
     * listens to incoming messages from socket
     * @param clientSocket the client socket
     **/
    public void run() {
        try {
            BufferedReader socIn = null;

            socIn = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            while (true) {
                String line=socIn.readLine();
                System.out.println(line);
            }
        } catch (Exception e) {
            System.err.println("Error in Client Receive Thread:" + e);
        }
    }

}
