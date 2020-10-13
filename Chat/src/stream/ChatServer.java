/***
 * ChatServer
 * TCP server for Chat
 * Date: 13/10/20
 * Authors: Stefan Ristovski Aydin Akaydin
 */

package stream;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ChatServer  {

    public static List<Socket> users;

    /**
     * main method
     * @param args port
     *
     **/
    public static void main(String args[]){
        ServerSocket listenSocket;
        users = null;

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

                //VERIFY IF THIS IS HOW TO USE GLOBAL VARIABLES
                //TREAT HOW IT WORKS WHEN USER DISCONNECTS
            }
        } catch (Exception e) {
            System.err.println("Error in ChatServer:" + e);
        }
    }
}