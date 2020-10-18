/***
 * ClientThreadServer (SERVER SIDE CLIENT THREAD)
 * Thread that receives message from client and sends them to the other clients
 * Date: 14/12/08
 * Authors:
 */

package stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;


public class ClientThreadServer
        extends Thread {

    private Socket clientSocket;
    private String name;

    ClientThreadServer(Socket s) {
        this.clientSocket = s;
        this.name = "Unknown user";
    }

    /**
     * receives a request from client then sends an echo to the client
     * @param clientSocket the client socket
     * @param users list of sockets associated with other clients
     **/
    public void run() {
        try {
            BufferedReader socIn = null;
            //reading from the client socket
            socIn = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            boolean hasName = false;

            //first received message is always the name of the user
            while (true && !hasName)
            {
                String line = socIn.readLine();
                //save the name and exit out of first listen loop
                if(!line.isEmpty())
                {
                    name = line;
                    hasName = true;
                    for (Socket s:stream.ChatServer.users)
                    {
                        //get the output stream for the socket and pass the message
                        PrintStream socOut = null;
                        try {
                            socOut = new PrintStream(s.getOutputStream());
                        } catch (IOException ioException) {
                            System.err.println("Error while sending join status message : " + ioException);
                        }
                        socOut.println(name + " has entered the chat." );
                    }
                }
            }

            //loop to listen to messages
            while (true) {
                String line = socIn.readLine();

                //if there is an actual message passed
                if(!line.isEmpty())
                {
                    //parse each socket
                    for (Socket s:stream.ChatServer.users)
                    {
                        //get the output stream for the socket and pass the message
                        PrintStream socOut = new PrintStream(s.getOutputStream());
                        socOut.println(name + " : " + line);

                    }
                }
            }
        } catch (SocketException e) {
            System.err.println("SocketException for user : " + name + " : " + e);

            ChatServer.users.remove(clientSocket);
            //parse each socket
            for (Socket s:stream.ChatServer.users)
            {
                //get the output stream for the socket and pass the message
                PrintStream socOut = null;
                try {
                    socOut = new PrintStream(s.getOutputStream());
                } catch (IOException ioException) {
                    System.err.println("Error while sending left status message : " + ioException);
                }
                socOut.println(name + " has left the chat." );
            }
        } catch (Exception e) {
            System.err.println("Error in ClientThread on Server Side:" + e);
        }
    }

}
