/***
 * ChatServer.
 * TCP server for Chat.
 * @since 13/10/20
 * @author Stefan Ristovski Aydin Akaydin
 */

package stream;

import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ChatServer  {

    /**
     * A list containing the sockets of all currently connected users
     **/
    public static List<Socket> users;

    /**
     * main method of the class. Creates a listening socket and accepts new connection.
     * For each new connection starts a client thread and adds it to the list of current users.
     * @param args port of server
     *
     **/
    public static void main(String args[]){
        ServerSocket listenSocket;
        users = new ArrayList<Socket>();

        if (args.length != 1) {
            System.out.println("Error in arguments! When compiling, please use: java ChatServer <ChatServer port>");
            System.exit(1);
        }
        try {
            //listening socket that waits for connection
            listenSocket = new ServerSocket(Integer.parseInt(args[0]));
            System.out.println("Server ready...");
            while (true) {
                //when we have a new connection, accept it, add the socket to list of users, and start the client thread on server side.
                Socket clientSocket = listenSocket.accept();
                System.out.println("Connexion from:" + clientSocket.getInetAddress());
                users.add(clientSocket);
                ClientThreadServer ct = new ClientThreadServer(clientSocket);
                ct.start();

            }
        } catch (Exception e) {
            System.err.println("Error in ChatServer:" + e);
        }
    }
}