/***
 * ChatClient.
 * TCP client for Client chat.
 * @since 13/10/20
 * @author Stefan Ristovski Aydin Akaydin
 */
package stream;

import java.io.*;
import java.net.*;



public class ChatClient {


    /**
     *  main method
     *  accepts a connection, starts up thread for send/receive of messages and passes the socket.
     * @param args accepts the host IP address and the port number in that order.
     **/
    public static void main(String[] args) throws IOException {

        Socket echoSocket = null;

        if (args.length != 2) {
            System.out.println("Usage: java EchoClient <EchoServer host> <EchoServer port>");
            System.exit(1);
        }

        //connect to socket
        try {
            // creation socket ==> connexion
            echoSocket = new Socket(args[0],Integer.parseInt(args[1]));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + args[0]);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to:"+ args[0]);
            System.exit(1);
        }

        System.out.println("Welcome to Messenger! Please enter your username: ");
        ClientSendThread sendThread = new ClientSendThread(echoSocket);
        ClientReceiveThread receiveThread = new ClientReceiveThread(echoSocket);
        sendThread.start();
        receiveThread.start();

    }
}


