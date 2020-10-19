/***
 * ReceiveThread.
 *
 * @since 13/10/20
 * @author Stefan Ristovksi, Aydin Akaydin
 */
package stream;

import java.net.*;
import java.io.*;

public class RecieveThread extends Thread{

    /**
     * Multiast client instance
     **/
	private MulticastClient client;
    /**
     * The multicast socket used for communication
     **/
	private MulticastSocket socket;
    /**
     * input buffer for messages
     **/
	private BufferedReader input;

    /**
     * Constructor. Assigns the client and socket.
     * @param c the multicast client
     * @param s the multicast socket
     **/
	public RecieveThread( MulticastSocket s, MulticastClient c) {
		socket = s;
		client = c;
	}

    /**
     * run method.
     * Listens for datahram packets (messages) from the group.
     **/
	public void run() {
  	  try { 
  		  
          for(;;) {
              byte[] buffer = new byte[2000];
              DatagramPacket pack = new DatagramPacket(buffer, buffer.length);
              socket.receive(pack);
              String s = new String(pack.getData(), 0, pack.getLength());
              s = s+"\n";
        	  client.writeMessage(s);
              }
      } catch (Exception e) {
          System.err.println("Error in ReceiveThread:" + e);
          e.printStackTrace();
      }
     }

	
}


